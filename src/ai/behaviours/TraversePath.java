package ai.behaviours;

import ai.pathfinding.Pathfinder;
import entities.MovingEntity;
import entities.enemies.Enemy;
import map.Tile;
import org.newdawn.slick.geom.Vector2f;

import java.util.*;

/**
 * Created by Chris on 9/10/2017.
 */
public class TraversePath {

	private boolean start = true;
	private boolean back=false;

	public enum KeyInput {
		GOLEFT,
		GORIGHT,
		JUMP,
	}
	boolean[] mInputs = new boolean[KeyInput.values().length];

	int[][] mGrid;
	MovingEntity entity;
	int maxJumpHeight;
	int charWidth;
	int charHeight;

	private int mGridRows;
	private int mGridCols;

	private Vector2f startNode;
	private Vector2f endNode;

	public List<Vector2f> path;

	Vector2f prevDest       = new Vector2f();
	Vector2f currDest       = new Vector2f();
	Vector2f nextDest       = new Vector2f();
	Vector2f targetPos;
	int currNodeID          = 1;        // Used to access path node indices

	public STATE state;
	public enum STATE {
		TRAVERSING,
		ENDOFPATH
	}

	/**
	 *
	 * @param mGrid The 2D map array containing the necessary information to call pathfinder
	 */
	public TraversePath(int[][] mGrid) {
		this.mGrid = mGrid;
	}

	public TraversePath(Tile[][] tiles) {
		if (tiles == null) throw new RuntimeException("Grid passed to pathfinder cannot be null");
		int[][] grid = new int[tiles[0].length][tiles.length];
		for(int j=0; j < tiles[0].length; j++){
			for(int i=0; i < tiles.length; i++){
				if(tiles[i][j] != null){
					grid[j][i] = 0;
				}
				else{
					grid[j][i] = 1;
				}
			}
		}
		this.mGrid = grid;
	}

	/**
	 * Called on in update() method run every tick to move entity position so it
	 * follows a path
	 * @param entity The start position of the pathfinder. Usually the AI
	 * @param targetP Where we want to move to? Could be a player, specific position of a tile etc. pos should be top left position in (x,y).
	 * @param targetHeight integer representation of how tall the target we are chasing is (if tile pass 1 in)
	 * @param maxJH integer representation of how high the entity can jump in number of tiles
	 * @param characterWidth integer representation of how wide the entity is
	 * @param characterHeight integer representation of how tall the entity is
	 */
	public synchronized void followPath(Enemy entity, Vector2f targetP, int targetHeight, int maxJH, int characterWidth, int characterHeight) {
		if(targetPos==null) {
			targetPos=targetP;
		}
		if (start||back) {
			init(entity,targetPos, maxJH, characterWidth, characterHeight);
			start = false;
			back=false;
		}

		if (state != STATE.ENDOFPATH) {
			if(path == null) {
				state = STATE.ENDOFPATH;
				return;
			}
			if(currNodeID>=path.size()) {
				state=STATE.ENDOFPATH;
				return;
			}
			currDest=path.get(currNodeID);
			int nextId=currNodeID+1;
			int prevId=currNodeID-1;
			int enX=Math.round(entity.getX()/32);
			int enY=Math.round(entity.getY()/32);
			int curX=Math.round(currDest.x);
			int curY=Math.round(currDest.y);

			if(nextId<path.size())nextDest=path.get(nextId);
			prevDest=path.get(prevId);
			if(enX==curX&&enY==curY){        	
				currNodeID++;
				if(currNodeID>=path.size()) {
					state=STATE.ENDOFPATH;
					currNodeID=1;
					return;
				}
			}

			if(enX < curX) {
				mInputs[KeyInput.GORIGHT.ordinal()]=true;
			}
			else if(enX > curX) {
				mInputs[KeyInput.GOLEFT.ordinal()]=true;
			}
			// If current entity position is lower than our destination, we need to jump
			boolean jump = false;
			if (enY > curY) {
				if (!isOutOfBounds(curX, curY + 1)) {
					if (isGround(curX,curY+1)) {
						jump = true;
					}
				}
			}
			if (jump) {
				mInputs[KeyInput.JUMP.ordinal()] = true;
			}
		}
		else{
			if(targetPos.x==entity.getAi().target.x){
				start=false;
				back=true;
				targetPos = entity.getAi().getStartPos();
			}
			else{
				start=true;
				back=false;
				targetPos = entity.getAi().target;
			}
		}
		entity.moveAI(mInputs);
		ResetInputs();
	}

	private void init(MovingEntity entity, Vector2f targetPos, int maxJH, int characterWidth, int characterHeight) {
		this.entity = entity;
		this.maxJumpHeight = maxJH;
		this.charWidth = characterWidth;
		this.charHeight = characterHeight;

		mGridRows = mGrid.length;
		mGridCols = mGrid[0].length;

		Vector2f startPos = new Vector2f(entity.getX(), entity.getY() + (characterHeight - 1) * 32);
		getTileAboveNearestGround(getMapTileAtXY(startPos));
		startNode = getMapTileAtXY(startPos);

		getTileAboveNearestGround(targetPos);
		endNode = targetPos;

		Pathfinder pf = new Pathfinder(mGrid);
		List<Vector2f> pfPath = pf.findPath(startNode, endNode, maxJumpHeight, charHeight, charWidth);

		// We found a path to follow! sick breh
		if (pfPath != null) {
			path = new ArrayList<>();
			path.addAll(pfPath);
			currNodeID = 1;
			state = STATE.TRAVERSING;
		}
	}

	private void ResetInputs() {
		mInputs[KeyInput.GORIGHT.ordinal()] = false;
		mInputs[KeyInput.GOLEFT.ordinal()] = false;
		mInputs[KeyInput.JUMP.ordinal()] = false;
	}

	// Helper Functions

	/**
	 * Given an arbitrary vector on a 2D array, we want to find
	 * the tile just above the ground below the passed in vector
	 * and set that as our node destination instead of most likely
	 * where it is currently - the air (player in the air as target)
	 * @param pos
	 */
	private void getTileAboveNearestGround(Vector2f pos) {
		if (!isOutOfBounds(Math.round(pos.getX()), Math.round(pos.getY()))) {
			if (!isGround(Math.round(pos.getX()), Math.round(pos.getY()))) {
				while (!isOutOfBounds(Math.round(pos.getX()), Math.round(pos.getY()) + 1)) {
					if (isGround(Math.round(pos.getX()), Math.round(pos.getY()) + 1)) {
						break;
					}
					else {
						pos.y++;
					}
				}
			}
		}
	}

	/**
	 * Doesn't use world space xy values. Should be within bounds of the grid
	 * Should always be called after a isOutOfBounds method is called
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isGround(int x, int y) {
		return mGrid[y][x] == 0;
	}

	/**
	 * Checks whether x and y values are out of bounds of the map grid.
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isOutOfBounds(int x, int y) {
		return (x >= mGridCols || x < 0 ||
				y >= mGridRows || y < 0);
	}

	/**
	 * Given a Vector2f of an x,y position that map to a ti
	 * @param pos
	 * @return
	 */
	private Vector2f getMapTileAtXY(Vector2f pos) {
		int tileIndexX = Math.round(pos.getX() / 32); // Assuming size of 32
		// Shifting the y position to be the bottom left position
		int tileIndexY = Math.round(pos.getY() / 32);
		return new Vector2f(tileIndexX, tileIndexY);
	}
}
