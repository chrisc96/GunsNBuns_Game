package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LevelMenu extends BasicGameState{
	
	Image levelScreen;

	public LevelMenu(int levelmenu){
		
	}

	@Override
	public void init(GameContainer a, StateBasedGame b) throws SlickException {
		levelScreen = new Image("data/img/222grouplevelmenu.png");
	}

	@Override
	public void render(GameContainer a, StateBasedGame b, Graphics g) throws SlickException {
		levelScreen.draw(0,0);		
	}

	@Override
	public void update(GameContainer a, StateBasedGame b, int x) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		
		if ((posX>38 && posX<300) && (posY>510 && posY<590)){
			if(Mouse.isButtonDown(0)){
				b.enterState(2);
			}
		}
		else if ((posX>38 && posX<300) && (posY>407 && posY<487)){
			if(Mouse.isButtonDown(0)){
				b.enterState(3);

			}
		}
		else if ((posX>38 && posX<300) && (posY>300 && posY<382)){
			if(Mouse.isButtonDown(0)){
				b.enterState(4);

			}
		}
		else if ((posX>30 && posX<294) && (posY>27 && posY<118)){
			if(Mouse.isButtonDown(0)){
				b.enterState(0);
			}
		}
		
		
		
	}

	@Override
	public int getID() {
		return 1;
	}
}
