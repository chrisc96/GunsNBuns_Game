package camera;

import entities.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A CameraManager controls the creation of cameras for use of multiple cameras
 * @author Eli
 */
public class CameraManager {

    private static CameraManager instance;  // this is the only instance and is destroyed when all strong references are lost
    private int selectedCamera = 0; // the selected viewing camera
    private int currentLevel = 0; // to check the camera set
    private List<Camera> cameras = new ArrayList<>();

    /**
     * Creates and/or returns the instance of CameraManager
     * @return the CameraManager instance
     */
    public static CameraManager getInstance() {    //singleton
        return instance == null ? new CameraManager() : instance;
    }

    private CameraManager() {
        instance = this;
    }

    /**
     * Load cameras into the camera manager for the scene
     * @param cameras a List of Camera objects to be added
     */
    public void loadCameras(List<Camera> cameras) {
        this.cameras.addAll(cameras);
    }

    /**
     * Add a camera to the camera manager for the scene
     * @param camera the camera to be added
     */
    private void addCamera(Camera camera) {
        this.cameras.add(camera);
    }

    private void addTraversalCamera(List<Point> points) {
        addCamera(new TraversalCamera(0, 0, points));
    }

    private void addBLerpCamera(float x, float y) {
        addCamera(new BLerpCamera(x, y));
    }

    private void addLerpCamera(float x, float y) {
        addCamera(new LerpCamera(x, y));
    }

    private void addLinearCamera(float x, float y) {
        addCamera(new LinearCamera(x, y));
    }

    /**
     * Assign the player for all
     * @param player the player
     */
    public void assignPlayer(Player player) {
        cameras.forEach((p) -> {
            if (p instanceof LinearCamera) {
                ((LinearCamera)p).focusEntity(player);
            }
        });
    }

    /**
     * Retrieves a camera that has been previously created
     * @return the current camera
     */
    public Camera getCurrentCamera() {
        if (cameras.size() == 0) throw new Error("Camera's have not yet been set up.");
        return cameras.get(selectedCamera);
    }

    /**
     * Switch the current camera
     * @param id the camera id
     */
    public void setCurrentCamera(int id) {
        if (cameras.size() > id) {
            selectedCamera = id;
        }
        throw new IndexOutOfBoundsException("ID " + id + " is too large. Max id is: " + (cameras.size()-1));
    }

    /**
     * Remove a camera from the scene (cameras should be destroyed at the end of the scene)
     * @param id the camera id
     */
    public void destroyCamera(int id) {
        if (cameras.size() > id) {
            cameras.remove(id);
            updateIds();
        }
    }

    /**
     * Check if the camera manager has the correct cameras for the level
     * @param level the level id
     */
    public void checkCameraSet(int level, Player player) {
        if (level != currentLevel) {
            loadCameraSet(level, player);
        }

    }

    /**
     * Reset the id's of all the cameras
     * (this is called after a camera is added/removed from the scene)
     */
    private void updateIds() {
        for (int i = 0; i < cameras.size(); i++) {
            cameras.get(i).updateId(i);
        }
    }

    /**
     * Load the cameras required for a specified level
     * @param level the level id
     */
    private void loadCameraSet(int level, Player player) {
        if (level < 8 && level > 4) cameras.clear();
        switch (level) {
            case 5:
            case 6:
            case 7:
                // example with a single traversal camera followed by a blerp camera
                int mapEnd = (int)(64 * 32 - LinearCamera.CAM_WIDTH);      // map width * tile width - camWd
                int mapHeight = (int)(18 * 32 - LinearCamera.CAM_HEIGHT);
                ArrayList<Point> path = new ArrayList<>();
                path.add(new Point(0, 0));
                path.add(new Point(mapEnd, 0));
                path.add(new Point(mapEnd, mapHeight));
                path.add(new Point(0, mapHeight));
                addTraversalCamera(path);
                addBLerpCamera(0, mapHeight);
                break;
            default:
                System.out.println("Level "+level+" cameras not found.");
                return;
        }
        assignPlayer(player);
        this.currentLevel = level;
    }
}