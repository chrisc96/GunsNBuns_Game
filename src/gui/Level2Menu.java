package gui;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level2Menu extends BasicGameState{

	Image levelScreen;
	Image level2;
	Image locked;
	public static boolean unlockedlvl2 = false;

	public Level2Menu(int levelmenu){
		
	}

	@Override
	public void init(GameContainer a, StateBasedGame b) throws SlickException {
		levelScreen = new Image("data/img/222grouplevelmenu.png");
		locked = new Image("data/img/locked.png");
		level2 = new Image("data/img/level2.png");
	}

	@Override
	public void render(GameContainer a, StateBasedGame b, Graphics g) throws SlickException {
		levelScreen.draw(0,0);
		if(unlockedlvl2) level2.draw(330, 150);
		else locked.draw(330, 150);
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
		else if ((posX>500 && posX<770) && (posY>97 && posY<184)){
			if(Mouse.isButtonDown(0)){
				if (unlockedlvl2){
					b.enterState(6);
				}
			}
		}	

	}

	@Override
	public int getID() {
		return 3;
	}
}