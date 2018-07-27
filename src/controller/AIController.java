package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.enemies.Enemy;

public class AIController {
	private List<Enemy> enemies;
	static HashMap<Enemy, List<Integer>> inputs=new HashMap<>();

	public AIController(List<Enemy> enemies){
		this.setEnemies(enemies);
	}

	public static void MoveRight(Enemy e){

		if(inputs.get(e)==null){
			inputs.put(e, new ArrayList<>());
		}
		inputs.get(e).add(2);
	}

	public static void MoveLeft(Enemy e){
		if(inputs.get(e)==null){
			inputs.put(e, new ArrayList<>());
		}
		inputs.get(e).add(0);
	}

	public static void Jump(Enemy e){
		if(inputs.get(e)==null){
			inputs.put(e, new ArrayList<>());
		}
		inputs.get(e).add(1);
	}

	public static void handle(int step){
		for(Enemy e : inputs.keySet()){
			for(Integer i:inputs.get(e)){
				if(i==0){
					e.moveLeft(step);
				}else if(i==1){
					e.jump();
				}else if(i==2){
					e.moveRight(step);
				}
			}
		}
		inputs=new HashMap<>();
	}
	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}
}
