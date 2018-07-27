package ai.pathfinding;

public class AStarNode{

    int totalCost = 0; // Cost from start
    int costFromStart = 0; // Cost from start + heuristic to goal (manhattans)
    int PX;
    int PY;
    int status;
    int PZ;
    int jumpHeight;

    public AStarNode() {}

    public void updateStatus(int mCloseNodeValue) {
        status = mCloseNodeValue;
    }
}
