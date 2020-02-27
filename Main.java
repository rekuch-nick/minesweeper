package minesweeper;

public class Main {
	
	public static final int X_BOUNDS = 21;
	public static final int Y_BOUNDS = 11;
	
	
	public static void main(String[] args) {
		
		PlayerInput player = new PlayerInput();
		WorldBuilder world = new WorldBuilder(player);
		player.setWorld(world);
		
		System.out.println("Welcome to Minesweeper, text addition!");
		System.out.println("");
		
		world.setupWorld();
		
		GameLoop gameLoop = new GameLoop(player, world);
		gameLoop.play();
	}
}
