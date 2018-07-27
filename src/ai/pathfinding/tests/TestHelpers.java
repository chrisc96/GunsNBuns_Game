package ai.pathfinding.tests;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.newdawn.slick.geom.Vector2f;

import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by Chris on 25/09/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHelpers {

    // =====================================================================
    // Test Helpers
    // =====================================================================

    /**
     * Checks one list against another and stops execution if a difference is found
     * The last line that is output will highlight the difference. If no differences
     * are found, the function will execute fully
     * @param returnedPath path returned by the pathfinding algorithm
     * @param correctPath user inputted path that should be followed - to check against
     */
    public static void checkPath(List<Vector2f> returnedPath, List<Vector2f> correctPath) {
        if (returnedPath == null) fail("No valid path found");
        else {
            System.out.println("Pathfinder Path       Correct Path");
            for (int i = 0; i < Math.max(returnedPath.size(), correctPath.size()); i++) {
                Vector2f returned = null;
                Vector2f correct = null;
                System.out.printf("   ");
                if (i < returnedPath.size()) {
                    returned = returnedPath.get(i);
                    System.out.printf("(" + returnedPath.get(i).getX() + "," + returnedPath.get(i).getY() + ")");
                }
                System.out.printf("            ");
                if (i < correctPath.size()) {
                    correct = correctPath.get(i);
                    System.out.printf("(" + correctPath.get(i).getX() + "," + correctPath.get(i).getY() + ")");
                }
                if (returned.getX() != correct.getX() || returned.getY() != correct.getY()) {
                    System.out.println();
                    fail("Incorrect path returned. See differences above");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Given a path, it'll just print out the x,y coordinates of that path
     * @param path
     */
    public static void printPath(List<Vector2f> path) {
        if (path == null) System.out.println("Path to print is null, i.e pathfinder didn't find a path... oops?");
        else {
            for (Vector2f p : path) {
                System.out.println("(" + p.getX() + ", " + p.getY() + ")");
            }
        }
    }
}
