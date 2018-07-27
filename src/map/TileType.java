package map;

/**
 * Enum representing all of the different tile types in the game.
 * 
 * @author Daniel Van Eijck
 *
 */
public enum TileType {

	FLOORTILE("ground"),
	PLATFORMTILE("platform"),
	SPRINGTILE("spring"),
	SLOWTILE("slow"),
	FASTTILE("fast"),
	FINISH("finish");
	
	String name;
	
	TileType(String name){
		this.name =  name;
	}
	
	
}
