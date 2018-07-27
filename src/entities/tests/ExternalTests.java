package entities.tests;

import org.junit.Test;

import entities.Player;
import entities.weapons.*;

/**
 * External Testing done by:
 * 
 * @author Daniel Van Eijck
 *
 */
public class ExternalTests {

	@Test
	public void testNoGunShoot(){
		Player player = new Player(0, 0, null, 0, 0);
		Weapon gun = new Gun(0, 0, null, null, 5, 1);
		assert gun.isHasPlayer() == false;
		assert player.shoot() == false;
	}
	
	@Test
	public void testGunShoot() {
		Player player = new Player(0, 0, null, 0, 0);
		Weapon gun = new Gun(0, 0, null, null, 5, 1);
		assert gun.isHasPlayer() == false;
		player.addWeapon(gun);
		assert gun.isHasPlayer() == true;
		assert player.getSelectedWeapon() == gun;
		assert player.shoot() == true;
	}
	
	@Test
	public void testWeaponDelayUpgrade(){
		Weapon gun = new Gun(0, 0, null, null, 5, 1);
		assert gun.getDelay() == gun.getDelay(gun.getLevel());
		gun.upgrade();
		assert gun.getDelay() == gun.getDelay(gun.getLevel());
	}
}
