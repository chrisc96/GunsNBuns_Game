package ai.pathfinding.tests.jumping;

import ai.pathfinding.Pathfinder;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static ai.pathfinding.tests.TestHelpers.*;

/**
 * Tests pathfinding algorithm. Solely tests jumping
 * mechanics on a simulated 2D map grid.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Jumping_Random_03 {

    Pathfinder pf;

    /**
     * Tests a feasible jump from one ledge to another
     * Unfiltered path returned
     * Jump Height      = 2
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_01_JH2_CH1_Ledge() {
        int[][] grid = {
                {1, 1, 1, 1},
                {1, 1, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 1, 1},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(3, 0), 2, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 2));
        correctPath.add(new Vector2f(1, 1));
        correctPath.add(new Vector2f(1, 0));
        correctPath.add(new Vector2f(2, 0));
        correctPath.add(new Vector2f(3, 0));

        checkPath(path, correctPath);
    }

    /**
     * Tests an in-feasible jump from one ledge to another
     * Unfiltered path returned
     * Jump Height      = 1
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_01_JH2_CH1_Ledge_Fail() {
        int[][] grid = {
                {1, 1, 1, 1},
                {1, 1, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 1, 1},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(3, 0), 1, 1, 1);

        Assert.assertNull(path);
    }

    /**
     * Tests a feasible jump from one ledge to another
     * with a gap inbetween the jump between ledges.
     * I.E not just a vertical jump
     * Unfiltered path returned
     * Jump Height      = 2
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_02_JH2_CH1_Ledge() {
        int[][] grid = {
                {1, 1, 1, 1},
                {1, 1, 1, 0},
                {1, 1, 1, 1},
                {0, 0, 1, 1},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(3, 0), 2, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 2));
        correctPath.add(new Vector2f(2, 2));
        correctPath.add(new Vector2f(2, 1));
        correctPath.add(new Vector2f(2, 0));
        correctPath.add(new Vector2f(3, 0));

        checkPath(path, correctPath);
    }

    /**
     * Tests an in-feasible jump from one ledge to another
     * with a gap inbetween the jump between ledges.
     * I.E not just a vertical jump
     * Unfiltered path returned
     * Jump Height      = 1
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_02_JH2_CH1_Ledge_Fail() {
        int[][] grid = {
                {1, 1, 1, 1},
                {1, 1, 1, 0},
                {1, 1, 1, 1},
                {0, 0, 1, 1},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(3, 0), 1, 1, 1);

        Assert.assertNull(path);
    }

    /**
     * Tests a feasible jump over a hole in the floor
     * Should take the same path, irrespective of the jump height of the AI
     * Unfiltered path returned
     * Jump Height      = 1
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_03_JH1_CH1_Gap() {
        int[][] grid = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(4, 2), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 2));
        correctPath.add(new Vector2f(2, 2));
        correctPath.add(new Vector2f(2, 1));
        correctPath.add(new Vector2f(3, 1));
        correctPath.add(new Vector2f(3, 2));
        correctPath.add(new Vector2f(4, 2));

        checkPath(path, correctPath);
    }

    /**
     * Tests a feasible jump over a decent sized hole in the floor
     * Should take the same path, irrespective of the jump height of the AI
     * Unfiltered path returned
     * Jump Height      = 1
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_03_JH2_CH1_Gap3() {
        int[][] grid = {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 1, 1, 0, 0},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 3), new Vector2f(6, 3), 2, 1, 1);
        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 3));
        correctPath.add(new Vector2f(1, 3));
        correctPath.add(new Vector2f(2, 3));
        correctPath.add(new Vector2f(2, 2));
        correctPath.add(new Vector2f(3, 2));
        correctPath.add(new Vector2f(3, 1));
        correctPath.add(new Vector2f(4, 1));
        correctPath.add(new Vector2f(4, 2));
        correctPath.add(new Vector2f(5, 2));
        correctPath.add(new Vector2f(5, 3));
        correctPath.add(new Vector2f(6, 3));

        checkPath(path, correctPath);
    }

    /**
     * Tests an infeasible jump over a decent sized hole in the floor
     * Should take the same path, irrespective of the jump height of the AI
     * Unfiltered path returned. Should fail due to tiles above (no path)
     * Jump Height      = 2
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_03_JH2_CH1_Gap3_Fail() {
        int[][] grid = {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 1, 1, 0, 0},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 3), new Vector2f(6, 3), 2, 1, 1);

        Assert.assertNull(path);
    }

    /**
     * Tests a feasible jump from a crevise to a ledge above,
     * should only work on jump heights above 3
     * Unfiltered path returned
     * Jump Height      = 3
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_04_JH2_CH1_Ledge() {
        int[][] grid = {
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1, 1},
                {0, 0, 1, 1, 1, 1}
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 4), new Vector2f(4, 0), 3, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 4));
        correctPath.add(new Vector2f(0, 3));
        correctPath.add(new Vector2f(1, 3));
        correctPath.add(new Vector2f(1, 2));
        correctPath.add(new Vector2f(2, 2));
        correctPath.add(new Vector2f(2, 1));
        correctPath.add(new Vector2f(2, 0));
        correctPath.add(new Vector2f(3, 0));
        correctPath.add(new Vector2f(4, 0));

        checkPath(path, correctPath);
    }


    /**
     * Tests whether an AI with a height of 1 can fit through a
     * hole in the wall of the height of the AI
     * Unfiltered path returned
     * Jump Height      = 2
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_04_JH2_CH1_HoleInTheWall() {
        int[][] grid = {
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(1, 3), new Vector2f(3, 3), 2, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(1, 3));
        correctPath.add(new Vector2f(1, 2));
        correctPath.add(new Vector2f(2, 2));
        correctPath.add(new Vector2f(3, 2));
        correctPath.add(new Vector2f(3, 3));

        checkPath(path, correctPath);
    }

    /**
     * Tests whether an AI with a height of 2 can fit through a
     * hole in the wall of the size 1. Should fail
     * Unfiltered path returned
     * Jump Height      = 2
     * Character Height = 1
     */
    @Test
    public void  AI_Jump_04_JH2_CH1_HoleInTheWall_01() {
        int[][] grid = {
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0},
        };

        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(1, 3), new Vector2f(3, 3), 2, 2, 1);

        Assert.assertNull(path);
    }
}
