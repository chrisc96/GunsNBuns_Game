package ai.pathfinding.tests.falling;

import ai.pathfinding.Pathfinder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static ai.pathfinding.tests.TestHelpers.checkPath;

/**
 * Created by Chris on 27/09/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Falling_Tests_01 {

    private Pathfinder pf;

    @Test
    public void AI_TestJump_Falling() {
        int[][] grid =
                {
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 1
                        {1, 0, 0, 0, 1, 1, 1, 1}, // row = 2
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 3
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 4
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 5
                        {1, 1, 1, 1, 0, 0, 0, 0}, // row = 6
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 7
                        // col = 0  1  2  3  4  5  6  7
                };
        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(2, 1), new Vector2f(5, 5), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(2, 1));
        correctPath.add(new Vector2f(3, 1));
        correctPath.add(new Vector2f(4, 1));
        correctPath.add(new Vector2f(4, 2));
        correctPath.add(new Vector2f(5, 2));
        correctPath.add(new Vector2f(5, 3));
        correctPath.add(new Vector2f(5, 4));
        correctPath.add(new Vector2f(5, 5));

        checkPath(path, correctPath);
    }
}
