package physics;

import java.util.ArrayList;
import entities.MovingEntity;
import entities.weapons.Bullet;
import map.Level;
import map.Tile;

/**
 * Handles the physics of the game. Including gravity and collision detection 
 * 
 * Made using the help of a slick2d tutorial
 * credit to: http://frums.nl/index/showpost/7/
 * 
 * @author Daniel Van Eijck
 *
 */
public class Physics {

	private final float gravity = 0.0015f;

	public void applyPhysics(Level level, int step){
		this.handleCharacters(level, step);
	}

	private void handleCharacters(Level level, int step){
		for(MovingEntity e : level.getEntities()){
			//if the entity is not moving then decelerate 
			if(!e.isMoving() && !(e instanceof Bullet)){
				e.decelerate(step);
			}
			handleEntity(e, level, step);
		}
	}

	/**
	 * Determines if the specified entity is colliding with any tile on the map
	 * 
	 * @param entity to be checked
	 * @param mapTiles - tiles of the map
	 * @return true if the entity is colliding with any tile, false otherwise
	 */
	public boolean checkCollision(MovingEntity entity, Tile[][] mapTiles){
		ArrayList<Tile> tiles = entity.getBounds().getTilesAroundShape(mapTiles);
		for(Tile t : tiles){
			if(t != null){
				if (t.getBounds().checkCollision(entity.getBounds())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determines if the specified entity is on the ground
	 * 
	 * @param entity to be checked
	 * @param mapTiles - the tiles in the map
	 * @return true if the entity is on the ground, false otherwise
	 */
	public boolean isOnGround(MovingEntity entity, Tile[][] mapTiles){
		ArrayList<Tile> tiles = entity.getBounds().getTilesUnderShape(mapTiles);
		//move the entity down to ensure it is colliding with the tile below it
		entity.getBounds().update(entity.getX(), entity.getY() + 1);
		for(Tile t : tiles){
			if(t != null){
				if (t.getBounds().checkCollision(entity.getBounds())){
					//revert back to original position 
					entity.getBounds().update(entity.getX(), entity.getY() - 1);
					return true;
				}
			}
		}
		//revert back to original position 
		entity.getBounds().update(entity.getX(), entity.getY() - 1);
		return false;
	}

	/**
	 * Moves the entity in the world depending on their current x and y velocity values.
	 * The entity is moved 1 pixel at a time to check for collisions.
	 * 
	 * @param entity that is being moved
	 * @param level - current level in the game
	 * @param step - the tile in milliseconds since the last update
	 */
	private void handleEntity(MovingEntity entity, Level level, int step){
		//determine if the entity is on the ground and whether to apply gravity
		entity.setOnGround(isOnGround(entity, level.getTiles()));
		if(!entity.isOnGround() || entity.getYVelocity() < 0) {
			if(!(entity instanceof Bullet)){ 
				entity.applyGravity(gravity*step);
			}
		}
		else entity.setYVelocity(0);

		//calculate how much we need to move in each direction
		float xMove = entity.getXVelocity()*step;
		float yMove   = entity.getYVelocity()*step;

		//we will break this movement down into steps of 1 pixel at a time
		//each time we move by this step we check for a collision
		float yStep = 0;
		float xStep = 0;

		//determine which direction we are moving
		if(xMove != 0){
			yStep = Math.abs(yMove)/Math.abs(xMove);
			if(yMove < 0) yStep = -yStep;
			if(xMove > 0) xStep = 1;
			else xStep = -1;

			if((yStep > 1 || yStep < -1) && yStep != 0){
				xStep = Math.abs(xStep)/Math.abs(yStep);
				if(xMove < 0) xStep = -xStep;
				if(yMove < 0) yStep = -1;
				else yStep = 1;
			}
		}
		else if(yMove != 0){
			if(yMove > 0) yStep = 1;
			else yStep = -1;
		}

		//and then move in little steps until we are done moving
		while(xMove != 0 || yMove != 0){
			//move in the x direction
			if(xMove != 0){
				//after taking a step, update xMove
				if((xMove > 0 && xMove < xStep) || (xMove > xStep  && xMove < 0)){
					xStep = xMove;
					xMove = 0;
				}
				else xMove -= xStep;
				//then we move the entity
				entity.setX(entity.getX() + xStep);

				//if we collide then move the entity back 
				if(checkCollision(entity,level.getTiles())){
					entity.setX(entity.getX() - xStep);
					entity.setXVelocity(0);
					xMove = 0;
				}
			}

			//move in the y direction
			if(yMove != 0){
				//after each step, update yMove
				if((yMove > 0 && yMove < yStep) || (yMove > yStep  && yMove < 0)){
					yStep = yMove;
					yMove = 0;
				}
				else yMove -= yStep;
				//move the entity
				entity.setY(entity.getY()+yStep);
				//if we collide then move the entity back
				if(checkCollision(entity, level.getTiles())){
					entity.setY(entity.getY() - yStep);
					entity.setYVelocity(0);
					yMove = 0;
				}
			}
		}
	}
}
