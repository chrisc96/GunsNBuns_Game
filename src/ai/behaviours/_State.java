package ai.behaviours;

import entities.enemies.Enemy;
import java.util.List;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Chris on 20/09/2017.
 */
public abstract class _State {
	protected List<Vector2f> l;
    protected Enemy enemy;
    protected final Vector2f start;
    protected final Vector2f target;
    protected Vector2f dist; // Each behaviour will have a distance to change behaviour
	protected TraversePath traversePath;

    /**
     *
     * @param enemy entity that path finding will be called on
     * @param target Target is the tile map position of where we want to go
     */
    public _State(Enemy enemy, Vector2f start, Vector2f target, TraversePath tp) {
        this.enemy = enemy;
        this.target = target;
        this.start = start;
        this.traversePath = tp;
    }

    public abstract void execute();
    public abstract void changeBehaviour();

	public Vector2f getStartPos() {
		return this.start;
	}
}