package entities.enemies;

import ai.behaviours._State;
import org.newdawn.slick.Image;
/**
 * short range enemy
 * @author inti
 *
 */
public class MeleeEnemy extends Enemy {

	public MeleeEnemy(float x, float y, Image sprite, double LP, double damage, int coins, String name, _State ai) {
		super(x, y, sprite, LP, damage, coins, name, ai);
		this.maxSpeed = 0.1f;
	}



}
