package camera;

import java.awt.Point;

/**
 * @author Eli
 */
class HelperMethods {

    /**
     * A linear interpolation calculator
     * @param pos the initial position
     * @param des the desired position
     * @param t the step percentage
     * @return a float t% from pos to des
     */
    static float lerp(float pos, float des, float t) {
        return (1 - t) * pos + t * des;
    }

    /**
     * A bilinear interpolation calculator
     * @param pos the initial position
     * @param des the desired position
     * @param t the step percentage
     * @return the Point t% from pos to des
     */
    static Point bilerp(Point pos, Point des, float t) {
        return new Point((int)lerp(pos.x, des.x, t), (int)lerp(pos.y, des.y, t));
    }

}
