package ai.behaviours;

import org.newdawn.slick.geom.Vector2f;
import entities.enemies.Enemy;

/**
 * Created by Chris on 20/09/2017.
 */
public class Patrolling extends _State {

	/**
	 * @param enemy  entity that path finding will be called on
	 * @param target Target is tile position in map. not world coordinates
	 */
	public Patrolling(Enemy enemy, Vector2f start, Vector2f target, TraversePath tp) {
		super(enemy, start, target, tp);
		dist = new Vector2f(50.0f, 10.0f);
	}

	@Override
	public void execute() {
		traversePath.followPath(enemy, target, 1, 1, (int) enemy.getBounds().getWidth()/32, (int) enemy.getBounds().getHeight() / 32);

		enemy.render();
	}

	@Override
	public void changeBehaviour() {

	}
}
