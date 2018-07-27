package camera;

import entities.Entity;
import entities.MovingEntity;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;

/**
 * A camera which can focus on an Entity object's position
 * @author Eli
 */
public class LinearCamera extends Camera {
    
    protected static final float CAM_WIDTH = 750, CAM_HEIGHT = 450, X_OFFSET = 60, Y_OFFSET = 80, FIXED_X_OFFSET = 15;
    protected float currentXOffset, currentYOffset;
    protected Entity focus;

    LinearCamera(float x, float y) {
        super(x, y, CAM_WIDTH, CAM_HEIGHT);
    }
    
    /**
     * Set the camera to follow an entity
     * @param entity the entity to focus on
     */
    public void focusEntity(Entity entity) {
        focus = entity;
    }
    
    /**
     * Stop the camera from following an entity
     */
    public void loseFocus() {
        focus = null;
    }
    
    /**
     * Manually set the position of the camera,
     * @return whether or not the position was changed
     */
    public void setPosition(int x, int y) {
        if (focus == null) {
            super.setPosition(x, y);
        }
    }
    
    /**
     * Update the position of the camera to center on it's focus' position
     */
    public void updatePosition(TiledMap map) {
        if (focus != null) {
            checkAlignment();
            pos.setLocation(new Point((int)(focus.getX() - CAM_WIDTH / 2), (int)(focus.getY() - CAM_HEIGHT / 2)));
            constrainBounds(map);
        }
    }

    /**
     * If the camera has a MovingEntity focus, it will realign to "behind" the Entity based
     * on the MovingEntity's velocity
     */
    protected void checkAlignment() {
        if (focus instanceof MovingEntity) {
            float xVel = ((MovingEntity)focus).getXVelocity();
            float yVel = ((MovingEntity)focus).getYVelocity();
            // if vel is +ve then offset lerp towards right else lerp towards left
            currentXOffset = xVel > 0 ? HelperMethods.lerp(currentXOffset, X_OFFSET, 0.3f) : xVel < 0 ? HelperMethods.lerp(currentXOffset, -X_OFFSET, 0.3f) : 0;
            currentYOffset = yVel > 0 ? HelperMethods.lerp(currentYOffset, Y_OFFSET, 0.3f) : yVel < 0 ? HelperMethods.lerp(currentYOffset, -Y_OFFSET, 0.3f) : 0;
        }// else {
            // focus centered on the entity
            currentXOffset = currentYOffset = 0;
        //}
    }

    /**
     * Constrain the camera's position to the bounds of the map
     * @param map map to constrain to
     */
    protected void constrainBounds(TiledMap map) {
        float width = map.getWidth() * map.getTileWidth();
        float height = map.getHeight() * map.getTileHeight();
        if (getX() < 0) pos.x = 0;
        else if (getX() + CAM_WIDTH > width) pos.x = (int)(width - CAM_WIDTH);
        if (getY() < 0) pos.y = 0;
        else if(getY() + CAM_HEIGHT > height) pos.y = (int)(height - CAM_HEIGHT);
        
    }
}
