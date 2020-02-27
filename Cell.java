package minesweeper;

public class Cell {
	
	private WorldBuilder world;
	private int xPoint;
	private int yPoint;
	private boolean visible;
	private boolean mine;
	private boolean marked;
	
	public Cell(WorldBuilder world, int x, int y) {
		this.world = world;
		this.xPoint = x;
		this.yPoint = y;
		this.visible = false;
		this.mine = false;
		this.marked = false;
	}
	
	// begin getters and setters /////
	public void resetCell() {
		this.visible = false;
		this.mine = false;
		this.marked = false;
	}
	
	public int getX() { return xPoint; }
	public int getY() { return yPoint; }
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	public boolean isMine() {
		return this.mine;
	}
	public void setMine(boolean b) {
		this.mine = b;
	}
	
	public boolean isMarked() {
		return this.marked;
	}
	public void setMarked(boolean b) {
		this.marked = b;
	}
	public void flipMarked() {
		this.marked = !this.marked;
	}
	
	// end getters and setters /////
	
	
	public int adjacentMines() {
		int mines = 0;
		
		for(int x=xPoint-1; x<=xPoint+1; x++) {
			for(int y=yPoint-1; y<=yPoint+1; y++) {
				try {
					if(world.cell(x, y).isMine()) {
						mines ++;
					}
				} catch (IndexOutOfBoundsException expected) {
					// don't check tiles out of range
					// no action needed
				}
			}
		}
		return mines;
	}
	
	
	
	
}
