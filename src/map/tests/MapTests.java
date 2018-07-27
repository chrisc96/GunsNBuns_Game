package map.tests;

import org.junit.Test;
import map.*;

/**
 * @author Daniel Van Eijck
 *
 */
public class MapTests {

	@Test
	public void testTileInit(){
		Tile t = new Tile(10, 20, TileType.FLOORTILE);
		assert t.getX() == 10;
		assert t.getY() == 20;
	}

	@Test
	public void testTileEquals(){
		Tile t1 = new Tile(0, 0, TileType.FASTTILE);
		Tile t2 = new Tile(0, 0, TileType.FASTTILE);
		assert t1.equals(t2);

		t1 = new Tile(1, 5, TileType.FLOORTILE);
		t2 = new Tile(1, 5, TileType.FLOORTILE);
		assert t1.equals(t2);

		t1 = new Tile(1, 2, TileType.SLOWTILE);
		t2 = new Tile(2, 1, TileType.SLOWTILE);
		assert !t1.equals(t2);

		t1 = new Tile(3, 3, TileType.FLOORTILE);
		t2 = new Tile(3, 3, TileType.SPRINGTILE);
		assert !t1.equals(t2);
	}

	@Test
	public void testTileBoundsInit(){
		Tile t = new Tile(0, 0, TileType.FLOORTILE);
		assert t.getBounds().getX() == 0;
		assert t.getBounds().getY() == 0;
		assert t.getBounds().getWidth() == 32;
		assert t.getBounds().getHeight() == 32;
	}

	@Test
	public void testTileTypeInit(){
		Tile t = new Tile(0, 0, TileType.FLOORTILE);
		assert t.getTileType() == TileType.FLOORTILE;

		t = new Tile(0, 0, TileType.SPRINGTILE);
		assert t.getTileType() == TileType.SPRINGTILE;

		t = new Tile(0, 0, TileType.FLOORTILE);
		assert t.getTileType() != TileType.SPRINGTILE;
	}

	@Test
	public void testPauseWhilePlaying() {

	}

}
