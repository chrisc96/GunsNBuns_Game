package camera;

import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;

/**
 * A camera which can bilinear-interpolate towards an Entity
 */
public class BLerpCamera extends LinearCamera {

    private static final float T = 0.05f;   // T is the step percentage

    public BLerpCamera(float x, float y) {
        super(x, y);
    }

    @Override
    public void updatePosition(TiledMap map) {
        if (focus != null) {
            checkAlignment();
            Point des = new Point((int)(focus.getX()-CAM_WIDTH/2+ currentXOffset + FIXED_X_OFFSET), (int)(focus.getY()-CAM_HEIGHT/2+currentYOffset));
            Point newPos = HelperMethods.bilerp(pos, des, T);
            pos.setLocation(newPos);
            constrainBounds(map);
        }
    }

}
