package game;

import java.io.Serializable;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import gui.Level1Menu;
import gui.Level2Menu;
import gui.Level3Menu;
import gui.LevelMenu;
import gui.LoseScreen;
import gui.Menu;
import gui.WinScreen;
import map.LevelState;

public class Game extends StateBasedGame implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 237919211089184767L;

	public static final String gameName = "Guns 'n' Buns";

	public static final int WINDOW_WIDTH  = 1280;
    public static final int WINDOW_HEIGTH = WINDOW_WIDTH / 16 * 9;
	public static final int startMenu = 0;
	public static final int Levelmenu = 1;
	public static final int Level1menu = 2;
	public static final int Level2menu = 3;
	public static final int Level3menu = 4;
	public static final int PlayLevel1 = 5;
	public static final int PlayLevel2 = 6;
	public static final int PlayLevel3 = 7;
	public static final int YouWin = 8;
	public static final int GameOver = 9;

	public Game(String gameName) {
		super(gameName);
		this.addState(new Menu(startMenu));
		this.addState(new LevelMenu(Levelmenu));
		this.addState(new Level1Menu(Level1menu));
		this.addState(new Level2Menu(Level2menu));
		this.addState(new Level3Menu(Level3menu));
		this.addState(new LevelState(PlayLevel1));
		this.addState(new LevelState(PlayLevel2));
		this.addState(new LevelState(PlayLevel3));
		this.addState(new WinScreen(YouWin));
		this.addState(new LoseScreen(GameOver));
	}
	
	public Game(){
		super(gameName);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(startMenu).init(gc, this);
		this.getState(Levelmenu).init(gc, this);
		this.getState(Level1menu).init(gc, this);
		this.getState(Level2menu).init(gc, this);
		this.getState(Level3menu).init(gc, this);
		this.getState(PlayLevel1).init(gc, this);
		this.getState(PlayLevel2).init(gc, this);
		this.getState(PlayLevel3).init(gc, this);
		this.getState(YouWin).init(gc, this);
		this.getState(GameOver).init(gc, this);
        this.enterState(startMenu);
	}
	
	public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game("Guns-n-Buns"));
		app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGTH, false);
		app.setTargetFrameRate(60);
		app.start();
   }
}