package physics;

import java.util.ArrayList;

import map.Tile;
import org.newdawn.slick.geom.Rectangle;

/**
 * @author Daniel Van Eijck
 *
 */
@SuppressWarnings("serial")
public abstract class BoundingShape extends Rectangle {

	public BoundingShape(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/**
	 * Updates the position of the shape
	 * 
	 * @param x - new x position of the shape
	 * @param y - new y position of the shape
	 */
	public abstract void update(float x, float y);
	
	/**
	 * This method determines if this bounding shape intersects another bounding box
	 * 
	 * @param box to be checked
	 * @return true if this shape intersects with the other box
	 */
	public abstract boolean checkCollision(BoundingShape box);
	
	/**
	 * This method returns a list of tiles are located around this bounding box
	 * 
	 * @param tiles - the 2D array of tiles on the map 
	 * @return a list of tiles which the bounding box overlaps with
	 */
	public abstract ArrayList<Tile> getTilesAroundShape(Tile[][] tiles);
	
	/**
	 * @param tiles - the 2D array of tiles on the map 
	 * @return a list of tiles that are directly under the shape
	 */
	public abstract ArrayList<Tile> getTilesUnderShape(Tile[][] tiles);
	
}
