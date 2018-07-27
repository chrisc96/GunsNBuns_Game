package entities.items;

import java.awt.Color;

import org.newdawn.slick.Image;

import entities.Entity;
import physics.Interactions;
import entities.Player;


/**
 * Abstract class for all items
 * @author inti
 *
 */
public abstract class Item extends Entity {
public Item(float x, float y, Image sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}
protected Color color;
protected String name;
/**
 * Constructor
 * @param x x pos
 * @param y y pos
 * @param color color
 * @param name name
 * @param bounds bounding box
 * @param image image 
 * @throws SlickException 
 */

/**
 * activated this using another item
 * @param itm
 */
public abstract void activate(Entity itm);

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

public void interract(Entity e,Interactions i){
	Player p=(Player)e;
	p.addItem(this);
	i.remove(this);
	
}

}
