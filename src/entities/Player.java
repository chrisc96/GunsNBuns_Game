package entities;

import java.util.ArrayList;
import javax.swing.Timer;
import org.newdawn.slick.Image;
import entities.MovingEntity;
import entities.items.Item;
import entities.weapons.Weapon;
import physics.Interactions;

public class Player extends MovingEntity {

	public int bank = 100;
	private ArrayList<Weapon>weapons;
	private ArrayList<Item> items;
	private Weapon selectedWeapon;
	private Timer time;
	private boolean reloading = false;
	private int lives;

	public boolean isReloading() {
		return reloading;
	}

	public void setReloading(boolean reloading) {
		this.reloading = reloading;
	}

	public Player(float x, float y, Image sprite, double LP, double damage) {
		super(x, y, sprite, LP, 0);
		this.accelerationSpeed = 0.001f;
		this.maxSpeed = 0.15f;
		this.maxFallingSpeed = 0.5f;
		this.decelerationSpeed = 0.001f;
		this.weapons=new ArrayList<>();
		this.items=new ArrayList<>();
		this.lives = 3;
	}

	public void addWeapon(Weapon w) {
		getWeapons().add(w);
		damage=w.getDamage();
		selectedWeapon = w;
		w.setHasPlayer(true);
		this.changeSprite("characterarmour");
	}

	public boolean shoot() {
		if(this.selectedWeapon == null) return false;
		time=new Timer(selectedWeapon.getDelay(),(p)->reload());
		time.start();
		return true;
	}

	private void reload() {
		this.reloading=false;
		time.stop();
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void addItem(Item item) {
		this.items.add(item);

	}

	public boolean activate(Item i){
		if(!items.contains(i)){
			return false;
		}
		i.activate(this);
		items.remove(i);
		return true;
	}

	@Override
	public void interact(MovingEntity m, Interactions i) {
		return;
	}

	public Weapon getSelectedWeapon() {
		return this.selectedWeapon;
	}
	
	public void setSelectedWeapon(Weapon w){
		this.selectedWeapon = w;
		this.changeSprite("characterarmour");
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public int getLives(){
		return this.lives;
	}

	public void reset() {
		this.setX(64);
		this.setY(400);
		this.setXVelocity(0);
		this.setLP(100);
	}

	public void reduceLives() {
		this.lives--;
	}

}