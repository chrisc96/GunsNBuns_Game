package physics;

import entities.Entity;
import entities.MovingEntity;
import entities.enemies.Enemy;
import entities.items.Item;
import entities.items.Potion;
import entities.weapons.Weapon;
import map.Level;
import map.LevelState;
import map.Tile;

/**
 * Class which applies all of the interactions that occur in the game.
 * This includes interactions between the player and special tiles, items and other entities.
 *
 * @author Daniel Van Eijck
 *
 */
public class Interactions {

	private Level level;
	private LevelState levelState;

	public Interactions(Level level, LevelState levelState) {
		this.level = level;
		this.levelState = levelState;
	}

	/**
	 * Applies all of the interections in this class
	 */
	public void apply(){
		this.itemInteractions();
		this.enemyInteractions();
		this.tileInteractions();
		this.bulletInteractions();
	}

	/**
	 *Apply the interactions between the items and the player
	 */
	public void itemInteractions(){
		for(Entity e:level.getItems()){
			if(e instanceof Weapon){
				Weapon w = (Weapon) e;
				if(!w.isHasPlayer() && w.colidesWith(levelState.getPlayer())){
					w.interact(levelState.getPlayer());
					remove(w);
					break;
				}
			}
			else if(e instanceof Item){
				Item i = (Item) e;
				if(i.colidesWith(levelState.getPlayer())){
					if(i instanceof Potion) {
						i.activate(levelState.getPlayer());
					}else {
						levelState.getPlayer().addItem(i);
					}
					remove(i);
					break;
				}
			}
		}
	}

	/**
	 * Apply the interactions between the bullet and the entities
	 */
	public void bulletInteractions(){
		for(MovingEntity e1 : level.getEntities()){
			boolean stop = false;
			for(MovingEntity e2 : level.getEntities()){
				if(e1.equals(e2)) continue;
				if(e1.colidesWith(e2)){
					e1.interact(e2, this);
					stop = true;
					break;
				}
			}
			if(stop) break;
		}
	}

	/**
	 * Apply the interactions between the enemies and the player
	 */
	public void enemyInteractions(){
		for(Entity e : level.getEntities()){
			if(e instanceof Enemy){
				Enemy en = (Enemy) e;
				if(en.colidesWith(levelState.getPlayer())){
					en.interact(levelState.getPlayer(), this);
					break;
				}
			}
		}
		if(!levelState.getPlayer().isAlive()){
			levelState.getPlayer().reset();
			levelState.getPlayer().reduceLives();
			if(levelState.getPlayer().getLives() <= 0){
				levelState.changeLevel(9);
			}
		}
	}

	/**
	 * Apply the interactions between the player and the tiles under his feet.
	 */
	public void tileInteractions(){
		BoundingShape playerBounds = levelState.getPlayer().getBounds();
		Tile tile = null;
		for(Tile t : playerBounds.getTilesUnderShape(level.getTiles())){
			if(playerBounds.getCenterX() > t.getBounds().getX() && playerBounds.getCenterX() < t.getBounds().getMaxX()){
				tile = t;
			}
		}
		if(tile == null) return;
		switch (tile.getTileType()){
		case FASTTILE:
			levelState.getPlayer().setXVelocity(levelState.getPlayer().getXVelocity()*1.05f);
			break;
		case FLOORTILE:
			break;
		case PLATFORMTILE:
			break;
		case SLOWTILE:
			levelState.getPlayer().setXVelocity(levelState.getPlayer().getXVelocity()/1.2f);
			break;
		case SPRINGTILE:
			levelState.getPlayer().setYVelocity(-0.75f);
			break;
		case FINISH:
			levelState.changeLevel(levelState.getID() + 1);
			break;
		default:
			break;
		}
	}

	public void remove(Entity entity) {
		level.getEntities().remove(entity);
		level.getItems().remove(entity);
	}

}
