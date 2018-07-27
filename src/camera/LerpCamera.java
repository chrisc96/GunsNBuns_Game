package camera;

import org.newdawn.slick.tiled.TiledMap;

/**
 * A camera which can linearly-interpolate towards an Entity's x-position
 * it will linearly follow the Entity's y-position
 * @author Eli
 */
public class LerpCamera extends LinearCamera{

    private static final float T = 0.05f;   // T is ths step percentage

    public LerpCamera(float x, float y) {
        super(x, y);
    }

    @Override
    public void updatePosition(TiledMap map) {
        if (focus != null) {
            checkAlignment();
            float xPos = HelperMethods.lerp(pos.x - CAM_WIDTH/2 + currentXOffset + FIXED_X_OFFSET, focus.getX(), T);
            pos.setLocation(xPos, focus.getY() - CAM_HEIGHT/2 + currentYOffset);
            constrainBounds(map);
        }
    }

}
