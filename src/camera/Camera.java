package camera;

import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;

/**
 * The Camera class acts as the base class for all cameras,
 * it also stores a list of Camera's which can be used on the current scene
 * @author Eli
 */
public abstract class Camera {

    protected int id;
    protected Point pos;
    protected float wd, ht;

    Camera(float x, float y, float wd, float ht) {
        this.pos = new Point((int) x, (int) y);
        this.wd = wd;
        this.ht = ht;
    }

    /**
     * Set the camera's position (based on top-left alignment)
     *
     * @param x x-position
     * @param y y-position
     */
    public void setPosition(int x, int y) {
        pos.setLocation(x, y);
    }

    // abstract methods
    public abstract void updatePosition(TiledMap map);

    // setter
    protected void updateId(int id) {
        this.id = id;
    }

    // getters
    protected int id() {
        return id;
    }

    public float getX() {
        return (float) pos.x;
    }

    public float getY() {
        return (float) pos.y;
    }

    public float getWd() {
        return wd;
    }

    public float getHt() {
        return ht;
    }

}
