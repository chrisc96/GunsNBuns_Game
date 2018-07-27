package physics.tests;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import entities.Player;
import org.junit.Test;
import org.newdawn.slick.SlickException;
import map.Tile;
import map.TileType;
import physics.*;

/**
 * Simple tests to fix the core methods used for physics
 * 
 * @author Daniel Van Eijck
 *
 */
public class PhysicsTests {
	
	@Test
	public void testValidBoundingBoxCollision(){
		BoundingBox box1 = new BoundingBox(0, 0, 200, 200);
		BoundingBox box2 = new BoundingBox(50, 50, 200, 200);
		assert box1.checkCollision(box2) == true;
	}
	
	@Test
	public void testInvalidBoundingBoxCollision(){
		BoundingBox box1 = new BoundingBox(0, 0, 200, 200);
		BoundingBox box2 = new BoundingBox(201, 201, 20, 20);
		assert box1.checkCollision(box2) == false;
	}
	
	@Test
	public void testUpdateBoundingBoxPosition(){
		BoundingBox box1 = new BoundingBox(0, 0, 200, 200);
		box1.update(20, 30);
		assert box1.getX() == 20;
		assert box1.getY() == 30;
		assert box1.getMaxX() == 220;
		assert box1.getMaxY() == 230;
	}
	
	@Test
	public void testGetTilesAroundShape(){
		Tile[][] tiles = {{new Tile(0, 0, TileType.FLOORTILE), new Tile(1, 0, TileType.FLOORTILE), new Tile(2, 0, TileType.FLOORTILE)},
						  {new Tile(0, 1, TileType.FLOORTILE), new Tile(1, 1, TileType.FLOORTILE), new Tile(2, 1, TileType.FLOORTILE)},
						  {new Tile(0, 2, TileType.FLOORTILE), new Tile(1, 2, TileType.FLOORTILE), new Tile(2, 2, TileType.FLOORTILE)}};
		
		List<Tile> collection = Arrays.stream(tiles) 
			    .flatMap(Arrays::stream)
			    .collect(Collectors.toList());
		
		BoundingBox box1 = new BoundingBox(1*32, 1*32, 32, 32);
		assert box1.getTilesAroundShape(tiles).equals(collection);
	}
	
	@Test
	public void testPhysicsCollision() throws SlickException{
		Physics physics = new Physics();
		Player player = new Player(0, 0, null, 0, 0);
		Tile[][] tiles = new Tile[2][2];
		tiles[0][0] = new Tile(0, 0, TileType.FLOORTILE);
		assert physics.checkCollision(player, tiles) == true;
	}
	
	@Test
	public void testPhysicsOnGround(){
		Physics physics = new Physics();
		Player player = new Player(0, 0, null, 0, 0);
		Tile[][] tiles = new Tile[3][3];

		tiles[0][1] = new Tile(0, 1, TileType.FLOORTILE);
		
		assert physics.isOnGround(player, tiles) == true;
	}
	
}
