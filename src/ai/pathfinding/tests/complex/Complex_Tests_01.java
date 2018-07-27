package ai.pathfinding.tests.complex;

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
public class Complex_Tests_01 {

    private Pathfinder pf;

    @Test
    public void AI_Jump_Move_Fall_01_JH1_CH1() {
        int[][] grid =
                {
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 1
                        {1, 0, 0, 0, 0, 0, 0, 1}, // row = 2
                        {0, 0, 0, 0, 0, 0, 0, 0}, // row = 3
                // col = 0  1  2  3  4  5  6  7
                };
        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(7, 2), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(0, 1));
        correctPath.add(new Vector2f(1, 1));
        correctPath.add(new Vector2f(2, 1));
        correctPath.add(new Vector2f(3, 1));
        correctPath.add(new Vector2f(4, 1));
        correctPath.add(new Vector2f(5, 1));
        correctPath.add(new Vector2f(6, 1));
        correctPath.add(new Vector2f(7, 1));
        correctPath.add(new Vector2f(7, 2));

        checkPath(path, correctPath);
    }

    @Test
    public void AI_Jump_Move_Fall_01_JH1_CH1_Filtered() {
        int[][] grid =
                {
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 1
                        {1, 0, 0, 0, 0, 0, 0, 1}, // row = 2
                        {0, 0, 0, 0, 0, 0, 0, 0}, // row = 3
                        // col = 0  1  2  3  4  5  6  7
                };
        pf = new Pathfinder(grid);
        pf.setFilter(true);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(7, 2), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 1));
        correctPath.add(new Vector2f(6, 1));
        correctPath.add(new Vector2f(7, 2));

        checkPath(path, correctPath);
    }

    @Test
    public void AI_Jump_Move_Vault_Fall_02_JH1_CH1() {
        int[][] grid =
                {
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
                        {1, 1, 0, 1, 1, 1, 1, 1}, // row = 1
                        {1, 0, 0, 0, 0, 0, 0, 1}, // row = 2
                        {0, 0, 0, 0, 0, 0, 0, 0}, // row = 3
                // col = 0  1  2  3  4  5  6  7
                };
        pf = new Pathfinder(grid);
        pf.setFilter(false);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(7, 2), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(0, 1));
        correctPath.add(new Vector2f(1, 1));
        correctPath.add(new Vector2f(1, 0));
        correctPath.add(new Vector2f(2, 0));
        correctPath.add(new Vector2f(3, 0));
        correctPath.add(new Vector2f(3, 1));
        correctPath.add(new Vector2f(4, 1));
        correctPath.add(new Vector2f(5, 1));
        correctPath.add(new Vector2f(6, 1));
        correctPath.add(new Vector2f(7, 1));
        correctPath.add(new Vector2f(7, 2));

        checkPath(path, correctPath);
    }

    @Test
    public void AI_Jump_Move_Vault_Fall_02_JH1_CH1_Filtered() {
        int[][] grid =
                {
                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
                        {1, 1, 0, 1, 1, 1, 1, 1}, // row = 1
                        {1, 0, 0, 0, 0, 0, 0, 1}, // row = 2
                        {0, 0, 0, 0, 0, 0, 0, 0}, // row = 3
                // col = 0  1  2  3  4  5  6  7
                };
        pf = new Pathfinder(grid);
        pf.setFilter(true);
        List<Vector2f> path = pf.findPath(new Vector2f(0, 2), new Vector2f(7, 2), 1, 1, 1);

        List<Vector2f> correctPath = new ArrayList<>();
        correctPath.add(new Vector2f(0, 2));
        correctPath.add(new Vector2f(1, 1));
        correctPath.add(new Vector2f(2, 0));
        correctPath.add(new Vector2f(3, 1));
        correctPath.add(new Vector2f(6, 1));
        correctPath.add(new Vector2f(7, 2));

        checkPath(path, correctPath);
    }
}