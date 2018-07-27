package camera;

import entities.Entity;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.util.List;

/**
 * A camera to which follows pathways defined in a List of Point objects
 * @author Eli
 */
public class TraversalCamera extends LinearCamera {

    private List<Point> points;
    private int currentPoint = 0;
    private boolean initialized = false;

    TraversalCamera(float x, float y, List<Point> points) {
        super(x, y);
        this.points = points;
        this.initialized = true;
    }

    /**
     * Add a pathway and begin following
     * @param points the pathway for the camera to follow
     */
    public void applyPathway(List<Point> points) {
         this.points = points;
         this.initialized = true;
    }

    @Override
    public void updatePosition(TiledMap map) {
        if (initialized) {
            Point newPoint = HelperMethods.bilerp(pos, points.get(currentPoint), 0.05f);
            double distanceChanged = Point.distance(pos.x, pos.y, newPoint.x, newPoint.y);
            if (distanceChanged < 1) {
                currentPoint++;
            }
            setPosition(newPoint.x, newPoint.y);
            if (currentPoint == points.size()) {
                initialized = false;
                CameraManager.getInstance().destroyCamera(id());
            }
        }
        constrainBounds(map);
    }

    @Override
    public void focusEntity(Entity entity) {}

}
