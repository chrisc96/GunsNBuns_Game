package entities.enemies;

import ai.behaviours._State;
import org.newdawn.slick.Image;
import entities.items.Item;
/**
 * Boss type enemy
 * @author inti
 *
 */
public class Boss extends Enemy {
	private Item item;

	public Boss(float x, float y, Image sprite, double LP, double damage, int coins, String name, _State ai,
			Item item) {
		super(x, y, sprite, LP, damage, coins, name, ai);
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}


	
}
