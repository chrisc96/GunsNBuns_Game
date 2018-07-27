package entities;

import org.newdawn.slick.Image;
import physics.BoundingBox;
import physics.BoundingShape;

public abstract class Entity {

	protected float x;
	protected float y;
	protected Image image;
	protected BoundingShape bounds;

	public Entity(float x, float y, Image sprite) {
		this.x = x;
		this.y = y;
		this.image = sprite;
		if(sprite!=null){
			this.bounds = new BoundingBox(x, y, sprite.getWidth(),sprite.getHeight()) ;
		}
		else{
			bounds=new BoundingBox(x, y, 32,32);
		}
	}
	
	public boolean colidesWith(Entity e){
		return bounds.intersects(e.bounds);
	}
	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
		bounds.update(x, y);
	}

	public void setY(float y) {
		this.y = y;
		bounds.update(x, y);
	}

	public void render(){
		image.draw(x, y);
	}

	public Image getSprite() {
		return image;
	}

	public void setSprite(Image sprite) {
		this.image = sprite;
	}
	
	public BoundingShape getBounds() {
		return bounds;
	}

	public void setBounds(BoundingShape bound) {
		this.bounds = bound;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		Entity m=(Entity)obj;
		if(m.x==x&&m.y==y){
			return true;
		}
		return false;
	}
}
