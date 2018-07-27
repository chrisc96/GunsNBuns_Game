package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controller.AIController;
import controller.PauseController;
import controller.PlayerController;
import gui.Level2Menu;
import gui.Level3Menu;
import physics.Interactions;
import physics.Physics;
import entities.Player;

/**
 * A LevelState which the game can be in. This is where the player interacts
 * with the level with the help of the physics library.
 * 
 * @author Daniel Van Eijck
 *
 */
public class LevelState extends BasicGameState {

	private StateBasedGame game;
	private GameContainer gc;
	private int stateID;
	private Level level;
	private String levelName;
	public int maxPlayerHealth = 100;

	private Player player;
	private PlayerController playerController;

	private Physics physics;
	private Interactions interactions;

	private Image heart;
	private Image pauseScreen;
	private Image upgradeScreen;
	private PauseController pauseController;
	public static boolean pause = false;
	public static boolean upgrade = false;

	/**
	 * Load the map arrording to the state
	 * 
	 * @param state - level to be loaded
	 */
	public LevelState(int state) {
		this.stateID = state;
		switch (state){
		case 5:
			this.levelName = "level1";
			break;
		case 6:
			this.levelName = "level2";
			break;
		case 7:
			this.levelName = "level3";
			break;
		}
	}

	/* 
	 * Initialize all of the objects accociated with the LevelState
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc=gc;
		this.game = sbg;
		this.level = new Level(levelName, this);
		this.level.attachFocus(this.level.getEntities().get(0));
		this.pauseScreen = new Image("data/img/pausemenu.png");
		this.upgradeScreen = new Image("data/img/upgrademenu.png");
		this.heart = new Image("data/img/heart.png");
		this.pauseController = new PauseController();
		this.playerController = new PlayerController(this.player, this.level);
		this.physics = new Physics();
		this.interactions=new Interactions(level, this);
	}

	/* 
	 * Render the level to the screen
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		this.level.render();
		int x = 100;
		for(int i = 0; i < player.getLives(); i++){
			heart.draw(x, 10);
			x += 40;
		}
		g.drawString("bank: "+Integer.toString(player.bank),gc.getWidth()-280-((Integer.toString(player.bank).length()+"bank : ".length())*10),10);
		g.drawString("health: "+player.getLP(), gc.getWidth()-100-((Double.toString(player.getLP()).length()+"health : ".length())*10), 10);
		if(pause==true) pauseScreen.draw(gc.getWidth()/2-pauseScreen.getWidth()/2, 50);
		if(upgrade==true) upgradeScreen.draw(gc.getWidth()/2-upgradeScreen.getWidth()/2, 50);
	}

	/* 
	 * This method applys the physics, interactions and player inputs to the game. 
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int step) throws SlickException {
		this.pauseController.handleInput(gc.getInput(), sbg);
		this.playerController.handleInput(gc.getInput(), step);
		this.physics.applyPhysics(level, step);
		AIController.handle(step);
		this.interactions.apply();
	}

	/**
	 * Change the level state
	 * 
	 * @param levelID - the level state to be entered
	 */
	public void changeLevel(int levelID){
		for(int i=5;i<=7;i++){
			LevelState l = (LevelState) this.game.getState(i);
			l.maxPlayerHealth=maxPlayerHealth;
			l.getPlayer().setLP(maxPlayerHealth);
			l.getPlayer().setItems(this.getPlayer().getItems());
			l.getPlayer().setSelectedWeapon(this.getPlayer().getSelectedWeapon());
		}
		if (this.getID() == 5){
			Level2Menu.unlockedlvl2 = true;
		}
		if (this.getID() == 6){
			Level3Menu.unlockedlvl3 = true;
		}
		this.game.enterState(levelID);
	}

	//helper methods

	public Level getLevel(){
		return this.level;
	}

	@Override
	public int getID() {
		return this.stateID;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static boolean getStatus(){
		return pause;
	}

	public GameContainer getGc() {
		return gc;
	}

	public void setGc(GameContainer gc) {
		this.gc = gc;
	}

}
