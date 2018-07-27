package ai.pathfinding;

import org.newdawn.slick.geom.Vector2f;

import java.util.*;

// Adapted from http://www.codeguru.com/csharp/csharp/cs_misc/designtechniques/article.php/c12527/AStar-A-Implementation-in-C-Path-Finding-PathFinder.htm
// and
// https://gamedevelopment.tutsplus.com/tutorials/how-to-adapt-a-pathfinding-to-a-2d-grid-based-platformer-theory--cms-24662
// Interpreted, adapted and converted to Java for our purposes

public class Pathfinder {

	private PriorityQueue<AStarNodeLocation> mOpen = null; // Nodes we haven't finished searching
	private List<Vector2f> mClose = null; // Nodes we have determined are dead and shouldn't be searched again
	private Stack<Integer> touchedLocations = null; // Nodes we have visited so we can clear the information in these easily when re-running
	private List<List<AStarNode>> nodes = null; // Nodes is a list of lists. The first list encapsulates the xy positions of node,
	// the second list is the AStarNodes at different z-depths of each xy position

	private int[][] mGrid = null;   // grid of a level map where the integer values represent states of each tile:
	// Either can move through or cannot (0, 1 respectively)

	private boolean mStop = false;
	private int mSearchLimit = 5000;    // How many nodes we can go through before we give us with path finding

	private int mOpenNodeValue = 1;     // value for a node we haven't finished searching (stored in status)
	private int mCloseNodeValue = 2;    // value for a node we have finished searching (stored in status)
	private int mH = 0;                 // heuristic value

	private AStarNodeLocation mLocation = null; // A field to store the current node we're exploring
	private int mNewLocation = 0;               // Index reference in nodes of this mLocation
	private int mLocationX = 0;                 // X index of node
	private int mLocationY = 0;                 // Y index of node
	private int mNewLocationX = 0;              // X index of node to compare to mLocationX
	private int mNewLocationY = 0;              // Y index of node to compare to mLocationY
	private int mNewTotalCost = 0;              // Total cost (totalCost + heuristic) of node to compare

	private int mCloseNodeCounter = 0;  // Used to calculate how many nodes we've been through to stop if we haven't found a path
	private boolean mFound = false;     // If we've found the target node

	private int mGridCols = 0;             // Grid Width
	private int mGridRows = 0;             // Grid Height
	//  Col
	private int[][] mDirection = new int[][]{   {0  , -1}, // LEFT
		{1  ,  0}, // DOWN
		{0  ,  1}, // RIGHT
		/* Row */ {-1 ,  0}};// UP


		private int mEndLocation = 0; // Integer index mapping to the correct place that the target node is in the nodes array
		private boolean filter = true;

		/**
		 *
		 * @param mGrid grid of map where the integer values represent states where:=
		 *                          0 = cannot move through,
		 *                          1 = empty cells
		 * IMPORTANT: Grid should be [rows][cols] not [cols][rows].
		 *            && the entities position in the 2d grid should be located at the bottom left index if the character
		 *            width and height is > 1
		 */
		public Pathfinder(int[][] mGrid) {
			if (mGrid == null) throw new RuntimeException("Grid passed to pathfinder cannot be null");

			this.mGrid = mGrid;
			mGridRows = mGrid.length;
			mGridCols = mGrid[0].length;
			if (nodes == null) {
				nodes = new ArrayList<>(mGridRows * mGridCols);
				touchedLocations = new SizedStack<>(mGridRows * mGridCols);
				mClose = new ArrayList<>(mGridRows * mGridCols);
			}

			for (int i = 0; i < mGridRows * mGridCols; i++) {
				nodes.add(new ArrayList<>());
			}

			Comparator<AStarNodeLocation> comp = new AStarComparator(nodes);
			mOpen = new PriorityQueue<>(comp);
		}


		/**
		 * @param indexStart a Vector2f (so x,y vector) that represents the starting position index of the pathfinder.
		 *                   Needs to correspond to a valid index in mGrid passed into the constructor of the pathfinder
		 * @param indexTarget a Vector2f (so x,y vector) that represents the target position index of the pathfinder.
		 *                   Needs to correspond to a valid index in mGrid passed into the constructor of the pathfinder
		 * @param maxCharacterJumpHeight the character's maximum jump height should be equal to a multiple of the tileset height
		 *                               (usually 32x32), thus passing 3 would be a maxJumpHeight of 3 * (tileHeight)
		 * @param characterHeight height in indexed tiles of the entity.
		 * @param characterWidth width in indexed tiles of the entity
		 */
		public List<Vector2f> findPath(Vector2f indexStart, Vector2f indexTarget, int maxCharacterJumpHeight, int characterHeight, int characterWidth) {
			while (touchedLocations.size() > 0) {
				nodes.get(touchedLocations.pop()).clear();
			}

			if (characterHeight == 0 || characterWidth == 0) return null;
			for (int i = 0; i < characterWidth; i++) {
				boolean inSolidTile = false; // If any part of the character is inside a solid tile
				for (int w = 0; w < characterWidth; w++) {
					for (int h = 0; h < characterHeight; h++) {
						if (!isOutOfBounds((int) indexTarget.getX() + w, (int) indexTarget.getY() - h)) {
							if (mGrid[(int) indexTarget.getY() - h][(int) indexTarget.getX() + w] == 0) {
								inSolidTile = true;
								break;
							}
						}
						else {
							inSolidTile = true;
							break;
						}
					}
					if (inSolidTile) break;
				}
				if (inSolidTile) {
					if (i == (characterWidth - 1)) return null;
					// Shift target position to the right to see if our width is causing
					// issues with actually being able to reach end position. Start position is from the bottom
					// left position, y decreases, x increases as char width gets larger
					indexTarget.x += 1;
				}
				else {
					break;
				}
			}

			mFound              = false;
			mStop               = false;
			mCloseNodeCounter   = 0;
			mOpenNodeValue      += 2;
			mCloseNodeValue     += 2;
			mOpen.clear();
			mClose.clear();

			mLocation = new AStarNodeLocation();
			mLocation.xy = ((int) indexStart.getY() * mGridCols) + (int) indexStart.getX();
			mLocation.z = 0;
			mEndLocation = ((int) indexTarget.getY() * mGridCols) + (int) indexTarget.getX();

			AStarNode firstNode = new AStarNode();
			firstNode.costFromStart = 0;
			firstNode.totalCost = 0;
			firstNode.PX = (int) indexStart.getX();
			firstNode.PY = (int) indexStart.getY();
			firstNode.PZ = 0;
			firstNode.status = mOpenNodeValue;

			// Checks if character's position at start is on the bottom tile
			boolean startsOnGround = false;
			for (int x = (int) indexStart.getX(); x < Math.round(indexStart.getX()) + characterWidth; x++) {
				if (!isOutOfBounds(x, Math.round(indexStart.getY() + 1))) {
					if (isGround(x, (int) indexStart.getY() + 1)) {
						startsOnGround = true;
						break;
					}
				}
			}
			if (startsOnGround) {
				firstNode.jumpHeight = 0;
			}
			else {
				firstNode.jumpHeight = maxCharacterJumpHeight * 2;
			}

			nodes.get(mLocation.xy).add(firstNode);
			touchedLocations.push(mLocation.xy);

			mOpen.add(mLocation);

			// Actually start
			while (mOpen.size() > 0 && !mStop) {

				mLocation = mOpen.poll();

				// Is it in the closed list? This means this node was already processed (visited)
				if (nodes.get(mLocation.xy).get(mLocation.z).status == mCloseNodeValue)
					continue;

				mLocationX = mLocation.xy % mGridCols;
				mLocationY = mLocation.xy / mGridCols;

				if (mLocation.xy == mEndLocation) {
					nodes.get(mLocation.xy).get(mLocation.z).updateStatus(mCloseNodeValue);
					mFound = true;
					break;
				}

				if (mCloseNodeCounter > mSearchLimit) {
					return null;
				}

				// Lets look at our four neighbours

				// Four conditions:
				// Character will not fit in this new position. I.E value = 0 in grid. Non pathable.
				// Character is on the ground if + 1 in the y pos the value == 0 and his current pos it's == 1 (non 0)
				// Character is just below a ceiling (y - 1 == 0) and y == 1. (I.E jumping in air at the ceiling (+1 in y == 0 is ceiling))
				// Character pos is == 1 and is neither on the ground or at the ceiling.
				for (int i = 0; i < mDirection.length; i++) {

					mNewLocationX = (mLocationX + mDirection[i][0]);
					mNewLocationY = (mLocationY + mDirection[i][1]);
					mNewLocation = (mNewLocationY * mGridCols) + mNewLocationX;

					boolean onGround = false;
					boolean atCeiling = false;

					//                // Check if this neighbour is out of bounds
					//                if (isOutOfBounds(mNewLocationX, mNewLocationY)) continue;

					// Taking into account entity height, if the top and bottom blocks are invalid
					boolean newPosInvalid = false;
					for (int w = 0; w < characterWidth; w++) {
						if (isOutOfBounds(mNewLocationX + w, mNewLocationY) ||
								isOutOfBounds(mNewLocationX + w, mNewLocationY - characterHeight + 1) ||
								isGround(mNewLocationX + w, mNewLocationY) ||
								isGround(mNewLocationX + w, mNewLocationY - characterHeight + 1)) {
							newPosInvalid = true;
							break;
						}

						if (isOutOfBounds(mNewLocationX + w, mNewLocationY + 1)) {
							newPosInvalid = true;
							break;
						}
						if (isGround(mNewLocationX + w, mNewLocationY + 1)) onGround = true;

						if (isOutOfBounds(mNewLocationX + w, mNewLocationY - characterHeight) &&
								!isOutOfBounds(mNewLocationX + w, mNewLocationY - characterHeight + 1)) {
							atCeiling = true;
						}
						else if (isOutOfBounds(mNewLocationX + w, mNewLocationY - characterHeight)) {
							newPosInvalid = true;
							break;
						}
						else if (mGrid[mNewLocationY - characterHeight][mNewLocationX + w] == 0) {
							atCeiling = true;
						}
					}
					// Checks if blocks left and right (taking into account entity width) are invalid
					for (int h = 1; h < characterHeight; h++) {
						if (isOutOfBounds(mNewLocationX, mNewLocationY - h) ||
								isOutOfBounds(mNewLocationX + characterWidth - 1, mNewLocationY - h) ||
								isGround(mNewLocationX, mNewLocationY - h) ||
								isGround(mNewLocationX + characterWidth - 1, mNewLocationY - h)) {
							newPosInvalid = true;
							break;
						}
					}
					if (newPosInvalid) continue;

					// Calculate the jumping value
					int jumpHeightParent = nodes.get(mLocation.xy).get(mLocation.z).jumpHeight;
					int newJumpHeight = jumpHeightParent;

					if (onGround) {
						newJumpHeight = 0;
					}
					// If character is at the ceiling then:
					else if (atCeiling) {
						if (mNewLocationX != mLocationX) {
							// If the character needs to drop straight down
							newJumpHeight = Math.max(maxCharacterJumpHeight * 2 + 1, jumpHeightParent + 1);
						}
						else {
							// If the character can still move one cell either side
							newJumpHeight = Math.max(maxCharacterJumpHeight * 2, jumpHeightParent + 2);
						}
					}
					// Calculating the jump value in mid air
					// If the new location is higher up than our current position, we're moving up
					// so calculate the new height length if the current jump length is odd, we increment
					// by one, forcing it to be even, if it's even, we increment it by two. Thus forcing an even value
					else if (mNewLocationY < mLocationY) {
						if (jumpHeightParent % 2 == 0) {
							newJumpHeight = jumpHeightParent + 2;
						}
						else {
							newJumpHeight = jumpHeightParent + 1;
						}
					}
					// If the new location is lower than our current position, we're moving down
					// We handle this the same as moving up except we set the minimum to the maxJumpHeight * 2
					// maxJumpHeight * 2 == when we're at the turning point (peak of the jump). Thus we immediately set
					// it to this so that the character initiates a fall
					else if (mNewLocationY > mLocationY) {
						if (jumpHeightParent % 2 == 0) {
							newJumpHeight = Math.max(maxCharacterJumpHeight * 2, jumpHeightParent + 2);
						}
						else {
							newJumpHeight = Math.max(maxCharacterJumpHeight * 2, jumpHeightParent + 1);
						}
					}
					else if (!onGround && mNewLocationX != mLocationX)
						newJumpHeight = newJumpHeight + 1;


					// If the jump value is odd, it means the character has moved left/right already. So block it as we want
					// the next node to be going up or down
					if (jumpHeightParent > 0 && jumpHeightParent % 2 != 0 && mLocationX != mNewLocationX) continue;

					// If the character is falling and the child node is above the parent then we should skip it (so once we
					// hit maxJumpHeight * 2 we can only go down.
					else if (jumpHeightParent >= maxCharacterJumpHeight * 2 && mNewLocationY < mLocationY) continue;

					// If we're falling fast as hell, make the AI fall more vertically than it's moving horizontally
					// (not 1 down one left/right). This forces it to move sideways every 4 blocks traversed vertically.
					else if (newJumpHeight >= maxCharacterJumpHeight * 2 + 6 && mNewLocationX != mLocationX &&
							(newJumpHeight - (maxCharacterJumpHeight * 2 + 6)) % 8 != 3) {
						continue;
					}

					// Calculate cost of the node:
					// + newJumpHeight/4 makes the character stick to the ground by changing the heuristic cost to be relative
					// to the jumpHeight. Thus jumping (a +2 in jumpHeight) is less favourable in terms of path finding than
					// along the ground.
					// TODO: /4 may need tinkering with
					mNewTotalCost = nodes.get(mLocation.xy).get(mLocation.z).costFromStart + mGrid[mNewLocationY][mNewLocationX] + newJumpHeight / 4;

					// Node has been processed, however we may need to visit this position in the grid again so we can't
					// set it to closed just yet.
					// The only condition at which we can skip this xy node is if the node doesn't allow
					// for any new movement compared to the other nodes on the same position (z value nodes)
					// Movement can happen if:
					// The currently processed node's jump value is less than any of the other nodes at the same x,y
					// This would mean that the current node promises to be able to jump higher than any other at the same x,y

					// The currently processed node's jump value is even and all the jump values at the x,y position are not
					// I.E this node allows for sideways movement and all the other nodes force either up or down movement
					if (nodes.get(mNewLocation).size() > 0) {
						int lowestJumpVal = Integer.MAX_VALUE;
						boolean canMoveSideways = false;

						for (int j = 0; j < nodes.get(mNewLocation).size(); ++j) {
							if (nodes.get(mNewLocation).get(j).jumpHeight < lowestJumpVal) {
								lowestJumpVal = nodes.get(mNewLocation).get(j).jumpHeight;
							}
							if (nodes.get(mNewLocation).get(j).jumpHeight % 2 == 0 && nodes.get(mNewLocation).get(j).jumpHeight < maxCharacterJumpHeight * 2 + 6)
								canMoveSideways = true;
						}

						// We skip the node if the lowestJumpVal is <= to the processed nodes jump value or any of the other
						// nodes in the list allowed for sideways movement
						if (lowestJumpVal <= newJumpHeight &&
								(newJumpHeight % 2 != 0 || newJumpHeight >= maxCharacterJumpHeight * 2 + 6 || canMoveSideways)) {
							continue;
						}
					}

					// Using manhattan formula for heuristic, works best for four tile movement with no diagonals
					mH = (int) ((Math.abs(mNewLocationX - indexTarget.getX()) + Math.abs(mNewLocationY - indexTarget.getY())));

					AStarNode newNode = new AStarNode();
					newNode.jumpHeight = newJumpHeight;
					newNode.PX = mLocationX;
					newNode.PY = mLocationY;
					newNode.PZ = mLocation.z;
					newNode.costFromStart = mNewTotalCost;
					newNode.totalCost = mNewTotalCost + mH;
					newNode.status = mOpenNodeValue;

					// need to set this so if we run the pathfinder again, it'll clear this locations list
					if (nodes.get(mNewLocation).size() == 0) {
						touchedLocations.push(mNewLocation);
					}

					nodes.get(mNewLocation).add(newNode);
					mOpen.add(new AStarNodeLocation(mNewLocation, nodes.get(mNewLocation).size()-1));
				}

				mCloseNodeCounter++;
				nodes.get(mLocation.xy).get(mLocation.z).updateStatus(mCloseNodeValue);
			}

			if (mFound) {
				mClose.clear();
				int posX = (int) indexTarget.x;
				int posY = (int) indexTarget.y;

				AStarNode fPrevNodeTmp = new AStarNode();
				AStarNode fNodeTmp = nodes.get(mEndLocation).get(0);

				Vector2f fNode = indexTarget;
				// This is the XY location in the grid of the node that will be processed on the next iteration
				int loc = (fNodeTmp.PY * mGridCols) + fNodeTmp.PX;
				while (fNode.x != fNodeTmp.PX || fNode.y != fNodeTmp.PY) {
					AStarNode fNextNodeTmp = nodes.get(loc).get(fNodeTmp.PZ);

					// Begin to filter the nodes that are returned so that we don't have heaps, only ones defined by these rules:
					// It's the start node
					// It's the end nodes
					// It's a jump node
					// It's a node with a jump value of 3 (side jump in air) (0 (ground) + 2 is vertical jump, +1 = 3 for horizontal movement)
					// It's a landing node (node had a non zero jump value becomes 0)
					// It's the turning point of the jump (maxCharJumpHeight * 2)
					// It's a node that goes round an obstacle... E.costFromStart our jump is the same height as another block
					if (filter) {
						boolean addNode = false;
						// Add end node
						if ((mClose.size() == 0) ||
								// Add jump nodes
								(fNextNodeTmp.jumpHeight != 0 && fNodeTmp.jumpHeight == 0) ||
								(fNodeTmp.jumpHeight == 3 && fPrevNodeTmp.jumpHeight != 0) ||
								// Add landing nodes
								(fNodeTmp.jumpHeight == 0 && fPrevNodeTmp.jumpHeight != 0) ||
								// Add jump turning point nodes (if both previous and next nodes are lowest that ours)
								(fNode.y < mClose.get(mClose.size() - 1).y && fNode.y < fNodeTmp.PY)) {
							// So far one of the above conditions has said yes, and thus we should add the node
							addNode = true;
							mClose.add(fNode);
						}

						// If none of the above simple rules for adding a filtered node then see if the node is a node
						// which goes round an obstacle - if we're next to an obstacle (left or right) and the previous
						// node we just added to the list is on a different row AND column, then it must be an obstacle
						if (!addNode) {
							boolean nextToObstacle = false;
							// Check if to the left and right aren't out of bounds and if they aren't check if there's an obstacle there
							if (!isOutOfBounds((int) fNode.x - 1, (int) fNode.y)) {
								if (isGround((int) fNode.x - 1, (int) fNode.y)) nextToObstacle = true;
							}
							if (!isOutOfBounds((int) fNode.x + 1, (int) fNode.y)) {
								if (isGround((int) fNode.x + 1, (int) fNode.y)) nextToObstacle = true;
							}

							if (nextToObstacle && fNode.y != mClose.get(mClose.size() - 1).y && fNode.x != mClose.get(mClose.size() - 1).x) {
								mClose.add(fNode);
							}
						}
					}
					else {
						mClose.add(fNode);
					}

					posX = fNodeTmp.PX;
					posY = fNodeTmp.PY;
					fPrevNodeTmp = fNodeTmp;
					fNodeTmp = fNextNodeTmp;
					loc = (fNodeTmp.PY * mGridCols) + fNodeTmp.PX;
					fNode = new Vector2f(posX, posY);
				}

				mClose.add(fNode);
				Collections.reverse(mClose); // populated backwards so reverse the list
				return mClose;
			}
			return null;
		}

		private boolean isGround(int x, int y) {
			return mGrid[y][x] == 0;
		}

		/**
		 * Returns false is x,y are out of bounds
		 * @param x
		 * @param y
		 * @return
		 */
		private boolean isOutOfBounds(int x, int y) {
			return (x >= mGridCols || x < 0 ||
					y >= mGridRows || y < 0);
		}

		public void setFilter(boolean filter) {
			this.filter = filter;
		}

		public int[][] getGrid() {
			// TODO Auto-generated method stub
			return mGrid;
		}
}
