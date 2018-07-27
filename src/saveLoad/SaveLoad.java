package saveLoad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import entities.MovingEntity;
import map.Level;

/**
 * Prints some data about the level into a file in order to save.
 * Reads the same data from the file and applies it to the level in order to load.
 * 
 * @author Daniel Van Eijck
 *
 */
public class SaveLoad {
	/**
	 * saves the current level
	 * @param level
	 */
	public static void save(Level level){
		try {
			PrintWriter writer = new PrintWriter(new File("saves/game.sav"));
			writer.println(level.getLevelState().getID());
			writer.println(level.getLevelState().getPlayer().getX());
			writer.println(level.getLevelState().getPlayer().getY());
			for(MovingEntity e : level.getEntities()){
				writer.println(e.getX());
				writer.println(e.getY());
				writer.println(e.getLP());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Save error");
			e.printStackTrace();
		}
	}

	/**
	 * loads the current saved level
	 * @return the loaded level
	 */
	public static void load(Level level){
		try {
			Scanner scanner = new Scanner(new File("saves/game.sav"));
			scanner.nextLine();
			level.getLevelState().getPlayer().setX(scanner.nextFloat());
			level.getLevelState().getPlayer().setY(scanner.nextFloat());
			for(MovingEntity e : level.getEntities()){
				e.setX(scanner.nextFloat());
				e.setY(scanner.nextFloat());
				e.setLP(scanner.nextDouble());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Load error");
			e.printStackTrace();
		}
	}

	/**
	 * Get the number of the last saved state
	 * 
	 * @return the number of the last accessed index if it exisits, -1 otherwise
	 */
	public static int getLatestState() {
		int ID = -1;
		try {
			Scanner scanner = new Scanner(new File("saves/game.sav"));
			ID = scanner.nextInt();
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Latest level state error");
			e.printStackTrace();
		}
		return ID;
	}

}
