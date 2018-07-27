package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoseScreen extends BasicGameState{

	Image loseScreen;
	int ID;
	
	public LoseScreen(int ID) {
		this.ID = ID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		loseScreen = new Image("data/img/losescreen.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics arg2) throws SlickException {
		loseScreen.draw(0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int step) throws SlickException {
		if(Mouse.isButtonDown(0)) {
			gc.reinit();
			sbg.init(gc);
		}
	}

	@Override
	public int getID() {
		return this.ID;
	}
}
