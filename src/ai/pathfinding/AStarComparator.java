package ai.pathfinding;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Chris on 24/09/2017.
 */
public class AStarComparator implements Comparator<AStarNodeLocation> {

    private List<List<AStarNode>> mMatrix;

    public AStarComparator(List<List<AStarNode>> mMatrix) {
        this.mMatrix = mMatrix;
    }

    @Override
    public int compare(AStarNodeLocation one, AStarNodeLocation two) {
        if (!mMatrix.isEmpty() && mMatrix.get(one.xy).get(one.z).totalCost > mMatrix.get(two.xy).get(two.z).totalCost)
            return 1;
        else if (mMatrix.get(one.xy).get(one.z).totalCost < mMatrix.get(two.xy).get(two.z).totalCost)
            return -1;
        return 0;
    }
}
