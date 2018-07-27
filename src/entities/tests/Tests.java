package entities.tests;

import org.junit.Test;

import org.newdawn.slick.SlickException;

import entities.Entity;
import entities.MovingEntity;
import entities.Player;
import entities.enemies.Enemy;
import entities.enemies.RangeEnemy;
import entities.items.Item;
import entities.items.Potion;
import entities.items.Treasure;
import entities.items.Upgrade;
import entities.weapons.Gun;
import entities.weapons.Sword;
import entities.weapons.Weapon;


public class Tests {
	@Test
	public void test1() throws SlickException{
		Entity enemy= new RangeEnemy(0, 0, null, 0, 0, 0, null, null);
		Entity weapon=new Sword(0, 0, null, null, 0, 0);
		Entity item = new Treasure(0, 0, null, 0);
		assert((enemy instanceof Enemy)||(weapon instanceof Weapon)||(item instanceof Item));
}
	@Test
	public void test2(){
		MovingEntity enemy= new RangeEnemy(0, 0, null, 0, 0, 0, null, null);
		enemy.setX(3);
		assert(enemy.getX()==3);
		enemy.setY(3);
		assert(enemy.getY()==3);
	}
	@Test
	public void test3(){
		Entity e=new Gun(0, 0, null, null, 0, 0);
		Entity e2=new Gun(0, 0, null, null, 0, 0);
		assert(e.colidesWith(e2));
	}
	@Test
	public void test4(){
		Enemy e=new RangeEnemy(0, 0, null, 100, 50,0,null,null);
		Enemy e2=new RangeEnemy(0, 0, null, 100, 50,0,null,null);
		e.attack(e2);
		assert(e2.getLP()==50);
		e2.hit(50);
		assert(e2.getLP()==0&&!e2.isAlive());
	}

	@Test
	public void test5(){
		MovingEntity p=new Player(0, 0, null, 0, 0);
		Potion po=new Potion(0, 0, null,30);
		po.activate(p);
		assert(p.getLP()==po.getRecovery());
	}

	@Test
	public void test6(){
		Player p=new Player(0, 0, null, 0, 0);
		Treasure t = new Treasure(0, 0, null, 50);
		t.activate(p);
		assert(p.bank==t.getCoins());
	}
	@Test
	public void test7(){
		Weapon w=new Sword(0, 0, null, null, 0, 1);
		Upgrade u =new Upgrade(0, 0, null);
		u.activate(w);
		assert(w.getLevel()==2&&w.getColor().equals(w.getColors().get(2)));
	}
}
