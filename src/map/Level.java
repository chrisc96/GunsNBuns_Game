package map;

import java.util.ArrayList;
import ai.behaviours.TraversePath;
import camera.*;
import entities.Entity;
import entities.MovingEntity;
import entities.Player;
import entities.enemies.Enemy;
import entities.enemies.MeleeEnemy;
import entities.enemies.RangeEnemy;
import entities.items.Parts;
import entities.items.Potion;
import entities.items.Treasure;
import entities.items.Upgrade;
import entities.weapons.Bullet;
import entities.weapons.Gun;
import game.Game;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import ai.behaviours.Patrolling;


/**
 * A Level object represents a complete level that the player can interact with
 * in the parent LevelState instance. A Level contains its own TiledMap and Camera which
 * are used to render the map to the screen. A Level also contains a list of moving entities
 * and a list of static entities.
 *
 * @author Daniel Van Eijck
 *
 */
public class Level {

	private LevelState state;
	private Tile[][] tiles;
	private TiledMap map;
	private ArrayList<MovingEntity> entities;
	private ArrayList<Entity> objects;
	private CameraManager cameraManager;
	private Image lvl1;
	private Image lvl2;
	private Camera camera;
	private int level = 0;

	public Level(String name, LevelState state) throws SlickException{
		this.state = state;
		this.map = new TiledMap("data/levels/" + name + ".tmx", "data/img");
		this.entities = new ArrayList<>();
		this.lvl1 = new Image("data/img/background.png");
		this.lvl2 = new Image("data/img/backgroundlvl2.png");
		this.tiles = loadCollisionLayer();
		this.objects = loadObjectLayer();
		this.cameraManager = CameraManager.getInstance();

		if(name == "level1") level = 1;
		else if(name == "level2") level = 2;
		else if(name == "level3") level = 3;
	}

	//for testing
	public Level() throws SlickException{
		this.entities = new ArrayList<>();
		this.tiles = loadCollisionLayer();
		this.objects = loadObjectLayer();
	}

	/**
	 * Renders the TiledMap and all of the enitities to the screen with the help of the camera
	 */
	public void render(){
		// check the cameras are correct
		cameraManager.checkCameraSet(getLevelState().getID(), getLevelState().getPlayer());
		Camera camera  = cameraManager.getCurrentCamera();
		// update camera position
		camera.updatePosition(getMap());
		// push matrix, transform view
		GL11.glPushMatrix();
		GL11.glScalef(Game.WINDOW_WIDTH/camera.getWd(), Game.WINDOW_HEIGTH/camera.getHt(), 1);
		GL11.glTranslatef(-camera.getX(), -camera.getY(), 0);
		if(level == 1){
			lvl1.draw(0, 0);
		}
		if(level == 2 || level == 3){
			lvl2.draw(0, 0);
		}
		map.render(0, 0, 0, 0, map.getWidth(), map.getHeight());

		// render characters
		for(MovingEntity e : entities){
			if(e.isAlive()){
				e.render();
				if(e instanceof Enemy){
					Enemy en=(Enemy)e;
					if(en.getAi()!=null){
						en.getAi().execute();
					}
				}
			}
		}
		//render items
		for(Entity e : objects){
			e.render();
		}

		// pop matrix
		GL11.glPopMatrix();
	}

	public Tile[][] loadCollisionLayer() throws SlickException{
		Tile[][] tiles = new Tile[map.getWidth()][map.getHeight()];
		int collisionLayer = map.getLayerIndex("CollisionLayer");
		//load tiles
		for (int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, collisionLayer);
				String name = map.getTileProperty(tileID, "Name", "None");
				switch (name){
				case "None":
					break;
				case "floor":
					tiles[x][y] = new Tile(x, y, TileType.FLOORTILE);
					break;
				case "platform":
					tiles[x][y] = new Tile(x, y, TileType.PLATFORMTILE);
					break;
				case "spring":
					tiles[x][y] = new Tile(x, y, TileType.SPRINGTILE);
					break;
				case "fast":
					tiles[x][y] = new Tile(x, y, TileType.FASTTILE);
					break;
				case "slow":
					tiles[x][y] = new Tile(x, y, TileType.SLOWTILE);
					break;
				case "finish":
					tiles[x][y] = new Tile(x, y, TileType.FINISH);
					break;
				default:
					break;
				}
			}
		}
		return tiles;
	}

	/**
	 * Loads all of the entities contained in the object layer layer in the TiledMap.
	 * This is where all of the enemies, items and weapons are loaded into the level.
	 * The player is also spawned here.
	 *
	 * @return an ArrayList of all of the entities in the level
	 * @throws SlickException
	 */
	public ArrayList<Entity> loadObjectLayer() throws SlickException {
		//load objects
		ArrayList<Entity> items = new ArrayList<>();
		for (int groupID = 0; groupID < map.getObjectGroupCount(); groupID++) {
			for (int objectID = 0; objectID < map.getObjectCount(groupID); objectID++) {
				String type = map.getObjectType(groupID, objectID);
				String name = map.getObjectName(groupID, objectID);
				int x = map.getObjectX(groupID, objectID);
				int y = map.getObjectY(groupID, objectID);
				switch (type){
				case "Weapon" :{
					if(name.equals("Gun")){
						items.add(new Gun(x, y, new Image("data/img/gun.png"), "gun", 5, 1));
					}
					if(name.equals("Sword")){
						items.add(new Gun(x, y, new Image("data/img/sword.png"), "sword", 5, 1));
					}
					break;
				}
				case "Item" :{
					if(name.equals("Potion")){
						items.add(new Potion(x, y, new Image("data/img/potion.png"), 100));
					}
					if(name.equals("Parts")){
						items.add(new Parts(x, y, new Image("data/img/parts.png")));
					}
					if(name.equals("Treasure")){
						items.add(new Treasure(x, y, new Image("data/img/parts.png"), 50));
					}
					if (name.equals("Upgrade")) {
						items.add(new Upgrade(x, y, new Image("data/img/gem.png")));
					}
					break;
				}
				case "Enemy" :{
					//target tile index for AI. default value is 0
					int startX = Integer.parseInt(map.getObjectProperty(groupID, objectID, "startX", "0"));
					int startY = Integer.parseInt(map.getObjectProperty(groupID, objectID, "startY", "0"));
					int targetX = Integer.parseInt(map.getObjectProperty(groupID, objectID, "targetX", "0"));
					int targetY = Integer.parseInt(map.getObjectProperty(groupID, objectID, "targetY", "0"));
					if(name.equals("MeleeEnemy")){
						Enemy e = new MeleeEnemy(x, y, new Image("data/img/enemy.png") , 20, 10, 1000000, "ememy", null);
						this.entities.add(e);
						e.setAi(new Patrolling(e, new Vector2f(startX, startY), new Vector2f(targetX, targetY), new TraversePath(tiles)));
					}
					if(name.equals("RangeEnemy")){
						Enemy e = new RangeEnemy(x, y, new Image("data/img/enemy.png") , 20, 10, 1000000, "enemy", null);
						this.entities.add(e);
						e.setAi(new Patrolling(e, new Vector2f(startX, startY), new Vector2f(targetX, targetY), new TraversePath(tiles)));
					}
					if(name.equals("Boss")){
						Enemy e = new MeleeEnemy(x, y, new Image("data/img/boss.png") , 70, 20, 1000000, "boss", null);
						this.entities.add(e);
						e.setAi(new Patrolling(e, new Vector2f(startX, startY), new Vector2f(targetX, targetY), new TraversePath(tiles)));
					}
					break;
				}
				case "Entity" :{
					if(name.equals("Player")){
						this.state.setPlayer(new Player(x, y, new Image("data/img/player.png"), state.maxPlayerHealth, 0));
						this.addEntity(this.state.getPlayer());
					}
					break;
				}
				default:
					throw new SlickException("invalid name/type");
				}
			}
		}
		return items;
	}

	/**
	 * Add a bullet to the level
	 *
	 * @param x
	 * @param y
	 * @param isRight - direction to set bullet
	 * @throws SlickException
	 */
	public void addBullet(float x, float y, boolean isRight) throws SlickException {
		Bullet b = new Bullet(x, y, new Image("data/img/bullet.png"), 1, state.getPlayer().getSelectedWeapon().getDamage());
		if(isRight) b.setXVelocity(1f);
		else b.setXVelocity(-1f);
		this.entities.add(b);
	}

	/**
	 * Focus the camera on an entity
	 * @param entity the entity to focus on
	 */
	public void attachFocus(Entity entity) {
		if (camera instanceof LinearCamera) ((LinearCamera)camera).focusEntity(entity);
	}

	/**
	 * @return the LevelState which this level belongs to
	 */
	public LevelState getLevelState(){
		return this.state;
	}

	//helper methods

	public ArrayList<MovingEntity> getEntities(){
		return this.entities;
	}

	public TiledMap getMap(){
		return this.map;
	}

	public Tile[][] getTiles(){
		return this.tiles;
	}

	public void addEntity(MovingEntity e) {
		this.entities.add(e);
	}

	public ArrayList<Entity> getItems() {
		return objects;
	}

	public void setItems(ArrayList<Entity> items) {
		this.objects = items;
	}

}
