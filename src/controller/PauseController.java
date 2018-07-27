package controller;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import entities.items.Upgrade;
import map.LevelState;
import saveLoad.SaveLoad;

public class PauseController {

	public void handleInput(Input input, StateBasedGame sbg) throws SlickException{
		LevelState l=(LevelState)sbg.getCurrentState();
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			LevelState.pause = true;
		}
		if (LevelState.pause == true && input.isKeyDown(Input.KEY_C)){
			LevelState.pause = false;
		}
		if (LevelState.pause == true &&  input.isKeyDown(Input.KEY_M)){
			LevelState.pause = false;
			sbg.enterState(0);
		}
		if (LevelState.pause == true &&  input.isKeyDown(Input.KEY_U)){
			LevelState.upgrade = true;
		}
		if (LevelState.pause == true && input.isKeyDown(Input.KEY_E)){
			LevelState state = (LevelState) sbg.getCurrentState();
        	SaveLoad.save(state.getLevel());
			System.exit(0);
		}
		if (LevelState.pause == true && input.isKeyDown(Input.KEY_S)){
			LevelState state = (LevelState) sbg.getCurrentState();
        	SaveLoad.save(state.getLevel());
			LevelState.pause = false;
		}
		if (LevelState.upgrade == true && input.isKeyPressed(Input.KEY_1)){
			for(int i=0;i<l.getPlayer().getItems().size();i++) {
				if(l.getPlayer().getItems().get(i) instanceof Upgrade) {
					Upgrade u = (Upgrade) l.getPlayer().getItems().get(i);
					u.activate(l);
					l.getPlayer().getItems().remove(i);
					break;
				}
			}
		}
		if (LevelState.upgrade == true && input.isKeyPressed(Input.KEY_2)){
			for(int i=0;i<l.getPlayer().getItems().size();i++) {
				if(l.getPlayer().getItems().get(i) instanceof Upgrade) {
					l.getPlayer().getItems().get(i).activate(l.getPlayer().getSelectedWeapon());
					l.getPlayer().getItems().remove(i);
					break;
				}
			}
		}
		if (LevelState.upgrade == true && input.isKeyDown(Input.KEY_ESCAPE)){
			LevelState.upgrade = false;
		}
	}


}
