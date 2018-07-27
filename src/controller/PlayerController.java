package controller;

import entities.Player;
import entities.weapons.Gun;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import map.Level;
import map.LevelState;

public class PlayerController {

	private Player player;
	private Level level;

	public PlayerController(Player player, Level level) {
		this.player = player;
		this.level = level;
	}

	public void handleInput(Input i, int speed) throws SlickException{
		boolean moving = false;
		if(!LevelState.getStatus()){
			if(i.isKeyDown(Input.KEY_R)){
				player.reset();
			}
			if(i.isKeyDown(Input.KEY_LEFT)){
				if(player.getX()-speed>0){
					player.moveLeft(speed);
					moving = true;
				}else{
					player.setX(10);
				}
			}
			if(i.isKeyDown(Input.KEY_RIGHT)){
				if(player.getX()+player.getBounds().getWidth()+speed<level.getMap().getWidth()*level.getMap().getTileWidth()){
					player.moveRight(speed);
					moving = true;
				}else{
					player.setX((level.getMap().getWidth()*level.getMap().getTileWidth())-player.getBounds().getWidth()-10);
				}
			}
			if(i.isKeyDown(Input.KEY_SPACE)){
				if(player.getSelectedWeapon() instanceof Gun){
					if(!player.isReloading()) {
						player.shoot();
						level.addBullet(this.player.getX(), this.player.getY()+this.player.getBounds().getHeight()/4, player.isRight());
						player.setReloading(true);
					}
				}
			}
			if(i.isKeyDown(Input.KEY_UP)){
				player.jump();
				moving = true;
			}
			else{
				player.decelerateY(speed);
			}
			if(player.getBounds().getY() > 20*32){
				player.reset();
				player.reduceLives();
				if(level.getLevelState().getPlayer().getLives() <= 0){
					level.getLevelState().changeLevel(9);
				}
			}
			player.setMoving(moving);
		}
	}
}
