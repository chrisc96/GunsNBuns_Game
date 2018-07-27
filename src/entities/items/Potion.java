package entities.items;


import org.newdawn.slick.Image;

import entities.Entity;
import entities.Player;
/**
 * potion item
 * @author inti
 *
 */
public class Potion extends Item {

	private int recovery;

	public int getRecovery() {
		return recovery;
	}

	public Potion(float x, float y, Image sprite, int recovery) {
		super(x, y, sprite);
		this.recovery=recovery;
	}

	@Override
	public void activate(Entity itm) {
		Player p=(Player)itm;
		p.hit(-recovery);
		
	}


}
