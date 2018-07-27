package map;

import physics.*;

/**
 * Class which represents a Tile. Each tile has a xy coordinate and a bounding box.
 * A tile also has a type, which determines how it will interact with the player.
 * 
 * @author Daniel Van Eijck
 *
 */
public class Tile {

	protected int x;
	protected int y;
	protected TileType type;
	protected BoundingShape bounds;
	
	public Tile(int x, int y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.bounds = new BoundingBox(x*32, y*32, 32, 32);
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public BoundingShape getBounds(){
		return this.bounds;
	}

	@Override
	public String toString() {
		return "x: "+x+" y: "+y;
	}

	public TileType getTileType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (type != other.type)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}