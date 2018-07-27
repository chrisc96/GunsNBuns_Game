package entities.items;


import org.newdawn.slick.Image;

import entities.Entity;
import entities.weapons.Weapon;
import map.LevelState;

/**
 * upgrade item
 * @author inti
 *
 */
public class Upgrade extends Item {






	public Upgrade(float x, float y, Image sprite) {
		super(x, y, sprite);
	}

	@Override
	public void activate(Entity itm) {
		if(itm instanceof Weapon) {
			Weapon w=(Weapon)itm;
			if(w.getLevel()<5){
				w.upgrade();
			}
		}

	}

	public void activate(LevelState l) {
		l.maxPlayerHealth += 10;
	}



}
