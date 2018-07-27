package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WinScreen extends BasicGameState{
	
	Image winScreen;
	int ID;
	
	public WinScreen(int ID) {
		this.ID = ID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		winScreen = new Image("data/img/winscreen.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		winScreen.draw(0, 0);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		if(Mouse.isButtonDown(0)) arg1.enterState(0);
	}

	@Override
	public int getID() {
		return this.ID;
	}

}
