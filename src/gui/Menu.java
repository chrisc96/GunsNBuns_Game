package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import map.LevelState;
import saveLoad.SaveLoad;

public class Menu extends BasicGameState{
	
	Image menuScreen;
	int ID;
	
	public Menu(int startmenu){
		this.ID = startmenu;
	}

	@Override
	public void init(GameContainer a, StateBasedGame b) throws SlickException {
		menuScreen = new Image("data/img/gamemenu.png");
	}

	@Override
	public void render(GameContainer a, StateBasedGame b, Graphics g) throws SlickException {
		menuScreen.draw(0,0);
		
	}

	@Override
	public void update(GameContainer a, StateBasedGame b, int x) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		//start
		if ((posX>407 && posX<875) && (posY>485 && posY<537)){
			if(Mouse.isButtonDown(0)){
		        b.enterState(1);

			}
		}
		//load
		else if ((posX>407 && posX<875) && (posY>392 && posY<449)){
			if(Mouse.isButtonDown(0)){
		        if(SaveLoad.getLatestState() != -1) {
		        	b.enterState(SaveLoad.getLatestState());
		        	LevelState state = (LevelState) b.getState(SaveLoad.getLatestState());
		        	SaveLoad.load(state.getLevel());
		        }
			}
		}
		//exit
		else if ((posX>407 && posX<875) && (posY>210 && posY<267)){
			if(Mouse.isButtonDown(0)){
		        System.exit(0);
			}
		}
	}

	@Override
	public int getID() {
		return this.ID;
	}
}