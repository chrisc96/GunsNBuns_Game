package entities.weapons;

import org.newdawn.slick.Image;

import entities.MovingEntity;
import entities.enemies.Enemy;
import physics.Interactions;

public class Bullet extends MovingEntity{

	public Bullet(float x, float y, Image sprite, double LP, double damage) {
		super(x, y, sprite, LP, damage);
	}

	@Override
	public void interact(MovingEntity m,Interactions i){
		if(this.getXVelocity()==0)return;
		if(m instanceof Enemy){
			Enemy p = (Enemy) m;
			p.attack(this);
			this.attack(m);
			if(!m.isAlive()) {
				i.remove(m);
			}
			if(!this.isAlive()){
				i.remove(this);
			}
		}
	}

}
