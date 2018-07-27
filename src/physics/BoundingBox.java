package physics;

import java.util.ArrayList;

import map.Tile;

@SuppressWarnings("serial")
public class BoundingBox extends BoundingShape{

	public BoundingBox(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public void update(float x, float y){
		super.setBounds(x, y, this.width, this.height);
	}

	public boolean checkCollision(BoundingShape r) {
		return this.intersects(r);
	}

	public ArrayList<Tile> getTilesAroundShape(Tile[][] tiles) {
		ArrayList<Tile> surroundingTiles = new ArrayList<Tile>();
		int tileIndexX = (int) (Math.round(x / 32)); 
		int tileIndexY = (int) (Math.round(y / 32));
		for (int x = tileIndexX-1; x <= tileIndexX + 1; x++) {
			for (int y = tileIndexY - 1; y <= tileIndexY + 1; y++) {
				if (x >= 0 && y >= 0 && x < tiles.length && y < tiles[0].length) {
					if(tiles[x][y] != null) surroundingTiles.add(tiles[x][y]);
				}
			}
		}
		return surroundingTiles;
	}

	public ArrayList<Tile> getTilesUnderShape(Tile[][] tiles) {
		ArrayList<Tile> tilesUnder = new ArrayList<Tile>();
		int tileIndexX = (int) (Math.round(x / 32)); 
		int tileIndexY = (int) (Math.round(y / 32));
		for (int x = tileIndexX-1; x <= tileIndexX + 1; x++) {
			if (tileIndexY + 1 < tiles[0].length && tileIndexY + 1 >= 0 && x >= 0 && x < tiles.length) {
				if(tiles[x][tileIndexY+1] != null) tilesUnder.add(tiles[x][tileIndexY+1]);
			}
		}
		return tilesUnder;
	}
}
