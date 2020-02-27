package minesweeper;

public class GameLoop {

	private PlayerInput player;
	private WorldBuilder world;
	private GameState gameState;
	
	
	public GameLoop(PlayerInput player, WorldBuilder world) {
		this.player = player;
		this.world = world;
		this.gameState = GameState.PLAY;
	}
	
	public void play() {
		
		boolean keepPlaying = true;
		while(keepPlaying) {
			
			if(gameState != GameState.HELP) {
				printScreen();
			}
			player.takeTurn(gameState);
			if(gameState != GameState.HELP) {
				gameState = world.checkGameOver();
			}
		}
		
		
	}
	
	private void printScreen() {
		//cls
		//for(int i=0; i<Main.Y_BOUNDS; i++) {
		//	System.out.println("");
		//}
		System.out.println("................................");
		
		// top cords
		String string = "    ";
		for(int i=0; i<Main.X_BOUNDS; i++) {
			if(i < 10) {
				string += "_" + i + "_";
			} else {
				string += "_" + i;
			}
		}
		
		System.out.println(string);
		
		// other rows
		for(int y=0; y<Main.Y_BOUNDS; y++) {
			string = "";
			if(y < 10) {
				string += "_" + y + "_ ";
			} else {
				string += "_" + y + " ";
			}
			
			for(int x=0; x<Main.X_BOUNDS; x++) {
				Cell cell = world.cell(x, y);
				
				if(cell.isMarked()) {
					string += "(!)";
				} else if(!cell.isVisible()) {
					string += "[?]";
				} else if(cell.isMine()) {
					string += "<X>";
				} else if(cell.adjacentMines() > 0) {
					string += " " + cell.adjacentMines() + " ";
				} else {
					string += " . ";
				}
				
			}
			
			
			System.out.println(string);
		}
		
	}
	

}

enum GameState {
	PLAY,
	WIN,
	LOSE, 
	HELP;
}
