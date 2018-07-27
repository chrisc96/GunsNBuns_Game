package entities.enemies;

import ai.behaviours._State;
import org.newdawn.slick.Image;

public class Thief extends  Enemy {
    public Thief(float x, float y, Image sprite, double LP, double damage, int coins, String name, _State ai) {
        super(x, y, sprite, LP, damage, coins, name, ai);
    }
}
