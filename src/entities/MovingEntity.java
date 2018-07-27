package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Mover;
import controller.AIController;
import entities.enemies.Enemy;
import physics.BoundingBox;
import physics.Interactions;

public abstract class MovingEntity extends Entity implements Mover {

	protected boolean moving = false;
	protected float accelerationSpeed = 1;
	protected float decelerationSpeed = 1;
	protected float xVelocity = 0;
	protected float yVelocity = 0;
	protected float maxFallingSpeed = 1;
	protected float maxSpeed = 0.15f;
	protected boolean onGround = false;
	protected double LP;
	protected double damage;
	protected Image flipped;
	protected Image sprite;
	protected boolean isRight=true;

	public MovingEntity(float x, float y, Image sprite,double LP,double damage) {	
		super(x, y, sprite);
		this.sprite=sprite;
		this.LP=LP;
		this.damage=damage;
		flipped=sprite==null?null:sprite.getFlippedCopy(true,false);
	}

	public abstract void interact(MovingEntity m,Interactions i);

	public void hit(double d){
		LP-=d;
	}
	public boolean isAlive(){
		return LP>0;
	}

	public void attack(MovingEntity m){
		m.hit(damage);
	}
	public void applyGravity(float gravity){
		if(yVelocity < maxFallingSpeed){
			yVelocity += gravity;
			if(yVelocity > maxFallingSpeed) yVelocity = maxFallingSpeed;
		}
	}

	public void decelerate(int step){
		if(xVelocity > 0){
			xVelocity -= decelerationSpeed*step;
			if(xVelocity < 0) xVelocity = 0;
		}
		else if(xVelocity < 0){
			xVelocity += decelerationSpeed*step;
			if(xVelocity > 0) xVelocity = 0;
		}
	}

	public void moveLeft(int step){
		if(xVelocity > -maxSpeed){
			xVelocity -= accelerationSpeed*step;
			if(xVelocity < -maxSpeed){
				xVelocity = -maxSpeed;
			}
		}
		moving = true;
	}

	public void moveRight(int step){
		if(xVelocity < maxSpeed){
			xVelocity += accelerationSpeed*step;
			if(xVelocity > maxSpeed){
				xVelocity = maxSpeed;
			}
		}
		moving = true;
	}

	public void jump() {
		if(onGround) {
			yVelocity = -0.45f;
			moving = true;
		}
	}

	public void decelerateY(int step){
		if(yVelocity < 0){
			yVelocity += decelerationSpeed*step;
			if(yVelocity > 0) yVelocity = 0;
		}
	}

	public void setMoving(boolean b) {
		this.moving = b;
	}

	public boolean isMoving(){
		return moving;
	}

	public boolean isOnGround(){
		return this.onGround;
	}

	public void setOnGround(boolean b){
		this.onGround = b;
	}

	public float getXVelocity(){
		return this.xVelocity;
	}

	public float getYVelocity(){
		return this.yVelocity;
	}

	public void setXVelocity(float vel){
		this.xVelocity = vel;
	}

	public void setYVelocity(float vel){
		this.yVelocity = vel;
	}

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public void setX(float x) {
		this.x = x;
		super.bounds.update(x, y);
	}

	public void setY(float y) {
		this.y = y;
		super.bounds.update(x, y);
	}

	public double getLP() {
		return LP;
	}

	public void setLP(double lP) {
		LP = lP;
	}


	public boolean isRight() {
		return isRight;
	}

	public void changeSprite(String image) {
		try {
			this.sprite = (new Image("data/img/"+image+".png"));
			flipped=sprite==null?null:sprite.getFlippedCopy(true,false);
			this.setBounds(new BoundingBox(x, y, sprite.getWidth(), sprite.getHeight()));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		catch(RuntimeException e){

		}
	}

	@Override
	public void render() {
		if(xVelocity!=0) {
			if(xVelocity > 0){
				isRight = true;
			}
			else{
				isRight = false;
			}
			image=xVelocity>0?sprite:flipped;
		}
		super.render();
	}


	// 0th index = move left
	// 0th index = move right
	// 0th index = jump

	public void moveAI(boolean[] mInputs) {
		if (mInputs[0]) {
			AIController.MoveLeft((Enemy) this);
		}
		if (mInputs[1]) {
			AIController.MoveRight((Enemy) this);
		}
		if (mInputs[2]) {
			AIController.Jump((Enemy) this);
		}
	}
}
