package ai.pathfinding.tests.jumping;

import ai.pathfinding.Pathfinder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static ai.pathfinding.tests.TestHelpers.checkPath;

/**
 * Tests pathfinding algorithm. Solely tests jumping
 * mechanics on a simulated 2D map grid.
 * This class tests a simple jump over a ledge
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Jumping_Simple_01 {

    private Pathfinder pf;

    /**
     * Unfiltered path result is returned - i.e no removal of nodes
     * Character jump height    = 1
     * Character height         = 1
     */
    @Test
    public void AI_Jump_01_JH1_CH1() {
        int[][] grid =
                {
                        {1, 1, 0, 0}, // row = 0
                        {1, 1, 0, 0}, // row = 1
                        {1, 0, 0, 0}, // row = 2
                        {0, 0, 0, 0}, // row = 3
                // col = 0  1  2  3
                };
        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(1, 1), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(0, 1));
        correctPath.add(new Vector2f(1, 1));

        checkPath(path, correctPath);
    }

    /**
     * Filtered path result is returned - certain unnecessary nodes are removed
     * Character jump height    = 1
     * Character height         = 1
     */
    @Test
    public void AI_Jump_01_Filtered_JH1_CH1() {
        int[][] grid =
                {
                        {1, 1, 0, 0}, // row = 0
                        {1, 1, 0, 0}, // row = 1
                        {1, 0, 0, 0}, // row = 2
                        {0, 0, 0, 0}, // row = 3
                        // col = 0  1  2  3
                };
        pf = new Pathfinder(grid);
        pf.setFilter(true);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(1, 1), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 1));

        checkPath(path, correctPath);
    }

    /**
     * Unfiltered path result is returned - i.e no removal of nodes
     * Character jump height    = 2
     * Character height         = 1
     */
    @Test
    public void AI_Jump_01_JH2_CH1() {
        int[][] grid =
                {
                        {1, 1, 0, 0}, // row = 0
                        {1, 1, 0, 0}, // row = 1
                        {1, 0, 0, 0}, // row = 2
                        {0, 0, 0, 0}, // row = 3
                        // col = 0  1  2  3
                };
        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(1, 1), 2, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(0, 1));
        correctPath.add(new Vector2f(1, 1));

        checkPath(path, correctPath);
    }

    /**
     * Filtered path result is returned - certain unnecessary nodes are removed
     * Character jump height    = 2
     * Character height         = 1
     */
    @Test
    public void AI_Jump_01_Filtered_JH2_CH1() {
        int[][] grid =
                {
                        {1, 1, 0, 0}, // row = 0
                        {1, 1, 0, 0}, // row = 1
                        {1, 0, 0, 0}, // row = 2
                        {0, 0, 0, 0}, // row = 3
                        // col = 0  1  2  3
                };
        pf = new Pathfinder(grid);
        pf.setFilter(true);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(1, 1), 2, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 1));

        checkPath(path, correctPath);
    }


    /**
     * Unfiltered path result is returned - i.e no removal of nodes
     * Character jump height    = 2
     * Character height         = 2
     */
    @Test
    public void AI_Jump_01_JH2_CH2() {
        int[][] grid =
                {
                        {1, 1, 0, 0}, // row = 0
                        {1, 1, 0, 0}, // row = 1
                        {1, 0, 0, 0}, // row = 2
                        {0, 0, 0, 0}, // row = 3
                // col = 0  1  2  3
                };
        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(1, 1), 2, 2, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(0, 1));
        correctPath.add(new Vector2f(1, 1));

        checkPath(path, correctPath);
    }

    /**
     * Filtered path result is returned - certain unnecessary nodes are removed
     * Character jump height    = 2
     * Character height         = 2
     */
    @Test
    public void AI_Jump_01_Filtered_JH2_CH2() {
        int[][] grid =
                {
                        {1, 1, 0, 0}, // row = 0
                        {1, 1, 0, 0}, // row = 1
                        {1, 0, 0, 0}, // row = 2
                        {0, 0, 0, 0}, // row = 3
                        // col = 0  1  2  3
                };
        pf = new Pathfinder(grid);
        pf.setFilter(true);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(1, 1), 2, 2, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 1));

        checkPath(path, correctPath);
    }
}
