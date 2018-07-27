package ai.pathfinding.externalTests;

import static ai.pathfinding.tests.TestHelpers.checkPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import ai.pathfinding.Pathfinder;

public class ExternalTests {
	 @Test
	    public void falling1() {
	        int[][] grid =
	                {
	                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
	                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 1
	                        {0, 0, 0, 1, 1, 1, 1, 1}, // row = 2
	                        {0, 0, 0, 0, 0, 0, 0, 0}, // row = 3
	                // col = 0  1  2  3  4  5  6  7
	                };
	       Pathfinder pf = new Pathfinder(grid);
	        pf.setFilter(false);
	        List<Vector2f> path = pf.findPath(new Vector2f(0, 1), new Vector2f(7, 2),1,1,1);
	        List<Vector2f> correctPath = new ArrayList<>();
	        correctPath.add(new Vector2f(0, 1));
	        correctPath.add(new Vector2f(1, 1));
	        correctPath.add(new Vector2f(2, 1));
	        correctPath.add(new Vector2f(3, 1));
	        correctPath.add(new Vector2f(3, 2));
	        correctPath.add(new Vector2f(4, 2));
	        correctPath.add(new Vector2f(5, 2));
	        correctPath.add(new Vector2f(6, 2));
	        correctPath.add(new Vector2f(7, 2));
	        checkPath(path, correctPath);
	    }
	 
	 @Test
	    public void falling2() {
	        int[][] grid =
	                {
	                        {1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
	                        {0, 1, 1, 1, 1, 1, 1, 1}, // row = 1
	                        {0, 0, 1, 1, 1, 1, 1, 1}, // row = 2
	                        {0, 0, 0, 1, 1, 0, 0, 0}, // row = 3
	                        {1, 1, 1, 0, 1, 1, 1, 1}, // row = 0
	                        {1, 1, 1, 1, 0, 1, 1, 1}, // row = 1
	                        {0, 0, 0, 1, 1, 0, 1, 1}, // row = 2
	                        {0, 0, 0, 0, 0, 0, 0, 0}, // row = 3
	               
	                };
	       Pathfinder pf = new Pathfinder(grid);
	        pf.setFilter(false);
	        List<Vector2f> path = pf.findPath(new Vector2f(0, 0), new Vector2f(7, 6),1,1,1);
	        List<Vector2f> correctPath = new ArrayList<>();
	        correctPath.add(new Vector2f(0, 0));
	        correctPath.add(new Vector2f(1, 0));
	        correctPath.add(new Vector2f(1, 1));
	        correctPath.add(new Vector2f(2, 1));
	        correctPath.add(new Vector2f(2, 2));
	        correctPath.add(new Vector2f(3, 2));
	        correctPath.add(new Vector2f(3, 3));
	        correctPath.add(new Vector2f(4, 3));
	        correctPath.add(new Vector2f(4, 4));
	        correctPath.add(new Vector2f(5, 4));
	        correctPath.add(new Vector2f(5, 5));
	        correctPath.add(new Vector2f(6, 5));
	        correctPath.add(new Vector2f(6, 6));
	        correctPath.add(new Vector2f(7, 6));
	        checkPath(path, correctPath);
	    }
	 
		@Test
		public void noPath1() {
			int[][] grid =
					{
							{1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
							{0, 1, 1, 1, 1, 1, 1, 1}, // row = 1
							{0, 0, 1, 1, 1, 1, 1, 1}, // row = 2
							{0, 0, 0, 1, 1, 0, 0, 0}, // row = 3
							{1, 1, 1, 0, 1, 1, 1, 1}, // row = 0
							{1, 1, 1, 1, 0, 1, 1, 1}, // row = 1
							{0, 0, 0, 1, 1, 0, 1, 1}, // row = 2
							{0, 0, 0, 0, 0, 0, 0, 0}, // row = 3

					};
			Pathfinder pf = new Pathfinder(grid);
			pf.setFilter(false);
			List<Vector2f> path = pf.findPath(new Vector2f(0, 0), new Vector2f(0, 6),1,1,1);
			assert(path==null);
		}

	@Test
	public void noPath2() {
		int[][] grid =
				{
						{1, 1, 1, 1, 1, 1, 1, 1}, // row = 0
						{0, 1, 1, 1, 1, 1, 1, 1}, // row = 1
						{0, 0, 1, 1, 1, 1, 1, 1}, // row = 2
						{0, 0, 0, 1, 1, 0, 0, 0}, // row = 3
						{1, 1, 1, 0, 1, 1, 1, 1}, // row = 0
						{1, 1, 1, 1, 0, 1, 1, 1}, // row = 1
						{0, 0, 0, 1, 1, 0, 1, 1}, // row = 2
						{0, 0, 0, 0, 0, 0, 0, 0}, // row = 3

				};
		Pathfinder pf = new Pathfinder(grid);
		pf.setFilter(false);
		List<Vector2f> path = pf.findPath(new Vector2f(0, 0), new Vector2f(0, 5),1,1,1);
		Assert.assertNull(path);
	}
	@Test
	public void noPath3() {
		int[][] grid =
				{
						{1, 0, 1, 1, 1, 1, 1, 1}, // row = 0
						{0, 0, 1, 1, 1, 1, 1, 1}, // row = 1
						{0, 0, 1, 1, 1, 1, 1, 1}, // row = 2
						{0, 0, 0, 1, 1, 0, 0, 0}, // row = 3
						{1, 1, 1, 0, 1, 1, 1, 1}, // row = 0
						{1, 1, 1, 1, 0, 1, 1, 1}, // row = 1
						{0, 0, 0, 1, 1, 0, 1, 1}, // row = 2
						{0, 0, 0, 0, 0, 0, 0, 0}, // row = 3

				};
		Pathfinder pf = new Pathfinder(grid);
		pf.setFilter(false);
		List<Vector2f> path = pf.findPath(new Vector2f(0, 0), new Vector2f(1, 0),1,1,1);
		Assert.assertNull(path);
	}
}
