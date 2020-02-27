package minesweeper;



public class WorldBuilder {
	
	private PlayerInput player;
	private Cell[][] world;
	
	WorldBuilder(PlayerInput player) {
		this.player = player;
		this.world = newWorld();
	}
	
	private Cell[][] newWorld() {
		Cell[][] world = new Cell[Main.X_BOUNDS][Main.Y_BOUNDS];
		
		for(int x = 0; x < Main.X_BOUNDS; x ++) {
			for(int y = 0; y < Main.Y_BOUNDS; y ++) {
				world[x][y] = new Cell(this, x, y);
			}
		}
		
		return world;
	}
	
	public Cell cell(int x, int y) {
		return world[x][y];
	}
	
	
	public void setupWorld() {
		resetWorld();
		setMines( getMines() );
	}
	
	private void resetWorld() {
		for(int x = 0; x < Main.X_BOUNDS; x ++) {
			for(int y = 0; y < Main.Y_BOUNDS; y ++) {
				world[x][y].resetCell();
			}
		}
	}
	
	private int getMines() {
		int maxMines = (Main.X_BOUNDS * Main.Y_BOUNDS) + 1;
		int normalMines = maxMines / 8;
		System.out.println("1 to " + maxMines + " mines will work.");
		System.out.println("I suggest about " + normalMines + ".");
		int n = player.getNumber("How many mines should I hide?", 1, maxMines);
		
		return n;
	}
	
	private void setMines(int mines) {
		while(mines > 0) {
			int x = (int)(Math.random() * Main.X_BOUNDS);
			int y = (int)(Math.random() * Main.Y_BOUNDS);
			Cell cell = world[x][y];
			
			if(!cell.isMine()) {
				cell.setMine(true);
				mines --;
			}
		}
	}
	
	
	
	public void clickCell(int x, int y, boolean firstClick) {
		if(x == -1 || y == -1) { return; }
		if(x >= Main.X_BOUNDS || y >= Main.Y_BOUNDS) { return; }
		if(world[x][y].isVisible()) { return; }
		if(world[x][y].isMine() && !firstClick) { return; }
		
		world[x][y].setVisible(true);
		if(world[x][y].adjacentMines() == 0) {
			clickCell(x, y-1, false);
			clickCell(x+1, y, false);
			clickCell(x, y+1, false);
			clickCell(x-1, y, false);
		}
		
		
		if(world[x][y].isMine()) {
			return;
		}
	}
	
	public void markCell(int x, int y) {
		if(x == -1 || y == -1) { return; }
		if(x >= Main.X_BOUNDS || y >= Main.Y_BOUNDS) { return; }
		if(world[x][y].isVisible()) { return; }
		
		world[x][y].flipMarked();
	}
	
	public void printHelp() {
		System.out.println("'2, 4' will mine the tile at x=2, y=4.");
		System.out.println("mark '2, 4' will mark the tile at x=2, y=4.");
		System.out.println("'exit' will quit the game.");
		System.out.println("You win if you've marked every mine without triggering it.");
		System.out.println("Good Luck!");
	}
	
	
	public GameState checkGameOver() {
		int totalSpaces = 0;
		int minedCells = 0;
		int markedMines = 0;
		int triggeredMines = 0;
		int totalMines = 0;
		for(int x = 0; x < Main.X_BOUNDS; x ++) {
			for(int y = 0; y < Main.Y_BOUNDS; y ++) {
				totalSpaces ++;
				if(world[x][y].isVisible()) {
					minedCells ++;
				}
				
				if(world[x][y].isMine()) {
					totalMines ++;
					if(world[x][y].isVisible()) {
						triggeredMines ++;
					} else if (world[x][y].isMarked()) {
						markedMines ++;
					}
				}
			}
		}
		
		if(triggeredMines > 0) {
			for(int x = 0; x < Main.X_BOUNDS; x ++) {
				for(int y = 0; y < Main.Y_BOUNDS; y ++) {
					world[x][y].setVisible(true);
				}
			}
			return GameState.LOSE;			
		} else if ( (markedMines >= totalMines) || totalSpaces - totalMines <= minedCells) {
			return GameState.WIN;
		}
		return GameState.PLAY;
	}
	
	
	
}
