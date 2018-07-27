package entities.items;


import org.newdawn.slick.Image;

import entities.Entity;
import entities.Player;
/**
 * tresure item
 * @author inti
 *
 */
public class Treasure extends Item{

	

	
	private int coins;

	

	public Treasure(float x, float y, Image sprite, int coins) {
		super(x, y, sprite);
		this.setCoins(coins);
	}



	@Override
	public void activate(Entity itm) {
		Player p=(Player)itm;
		p.bank+=getCoins();
		
	}



	public int getCoins() {
		return coins;
	}



	public void setCoins(int coins) {
		this.coins = coins;
	}



}
