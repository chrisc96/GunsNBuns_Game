package ai.pathfinding.tests;

import ai.pathfinding.tests.complex.Complex_Tests_01;
import ai.pathfinding.tests.falling.Falling_Tests_01;
import ai.pathfinding.tests.ground.Ground_Tests_01;
import ai.pathfinding.tests.jumping.Jumping_Random_03;
import ai.pathfinding.tests.jumping.Jumping_Simple_01;
import ai.pathfinding.tests.jumping.Jumping_Simple_02;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        // Complex tests
        Complex_Tests_01.class,

        // Falling tests
        Falling_Tests_01.class,

        // Ground tests
        Ground_Tests_01.class,

        // Jumping tests
        Jumping_Random_03.class,
        Jumping_Simple_01.class,
        Jumping_Simple_02.class,
})
public class CompleteTestSuite {}