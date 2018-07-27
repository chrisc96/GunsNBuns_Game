package entities.enemies;

import entities.items.Item;
import entities.weapons.Bullet;

import org.newdawn.slick.Image;
import ai.behaviours._State;
import entities.MovingEntity;
import physics.Interactions;
import entities.Player;

import java.util.ArrayList;

import javax.swing.Timer;

public abstract class Enemy extends MovingEntity {

	protected int coins;
	protected String name;
	protected _State ai;
	protected boolean reloading=false;
	protected ArrayList<Item>  item =new ArrayList<>();
	protected Timer time;

	public Enemy(float x, float y, Image sprite, double LP, double damage, int coins, String name,
			_State ai) {
		super(x, y, sprite, LP, damage);
		this.coins = coins;
		this.name = name;
		this.ai = ai;
	}

	public void interact(MovingEntity m, Interactions i){
		if(m instanceof Player){
			Player p = (Player) m;
			if(!reloading){
				reloading=true;
				this.attack(p);
				reload();
			}
			if(!this.isAlive()){
				p.bank += this.getCoins();
				i.remove(this);
			}
		}
		else if(m instanceof Bullet){
			Bullet b = (Bullet) m;
			if(b.getXVelocity()==0)return;
			b.attack(this);
			this.attack(b);
			if(!b.isAlive()) {
				i.remove(b);
			}
			if(!this.isAlive()){
				i.remove(this);
			}
		}
	}

	private void reload() {
		time=new Timer(500,(p)->setReload());
		time.start();

	}


	private void setReload() {
		time.stop();
		reloading=false;
	}

	public void move(_State ai){
		this.ai=ai;
	}

	public _State getAi() {
		return ai;
	}

	public void setAi(_State ai) {
		this.ai = ai;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addItem(Item i){
		item.add(i);
	}

}