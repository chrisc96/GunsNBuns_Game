package entities.weapons;

import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import entities.Entity;
import entities.MovingEntity;
import entities.Player;
/**
 * abstract class for all weapons
 * @author inti
 *
 */
public abstract class Weapon extends Entity {
	protected Color color;
	protected String name;
	protected double damage;
	protected int level;
	protected boolean hasPlayer=false;
	private HashMap<Integer,Color>colors;
	private HashMap<Integer,Integer> delays;
	private int delay;
	/**
	 * Constructor
	 * @param x x position
	 * @param y y position
	 * @param name the name
	 * @param damage how much damage it causes
	 * @param level the level of this weapon
	 * @throws SlickException
	 */
	public Weapon(float x, float y, Image sprite, String name,
			double damage, int level) {
		super(x, y, sprite);
		this.name = name;
		this.damage = damage;
		this.level = level;
		setColors(new HashMap<>());
		getColors().put(1,Color.gray);
		getColors().put(2,Color.green);
		getColors().put(3,Color.blue);
		getColors().put(4,Color.yellow);
		getColors().put(5,Color.red);
		this.color=getColors().get(level);
		delays=new HashMap<>();
		delays.put(1,600);
		delays.put(2,500);
		delays.put(3,400);
		delays.put(4,300);
		delays.put(5,200);
		this.delay=delays.get(level);

	}

	@Override
	public void render() {
		if(!hasPlayer){
			super.render();
		}
	}
	public void upgrade(){
		this.damage+=5;
		this.level++;
		this.color=getColors().get(level);
		delay=delays.get(level);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void doDamage(MovingEntity m){
		m.hit(damage);
	}

	public HashMap<Integer,Color> getColors() {
		return colors;
	}

	public void setColors(HashMap<Integer,Color> colors) {
		this.colors = colors;
	}

	public void interact(Player p) {
		p.addWeapon(this);

	}
	public boolean isHasPlayer() {
		return hasPlayer;
	}

	public void setHasPlayer(boolean hasPlayer) {
		this.hasPlayer = hasPlayer;
	}

	public int getDelay() {
		return delay;
	}

	public int getDelay(int level) {
		return delays.get(level);
	}

}
