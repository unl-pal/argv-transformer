package dungeonDigger.enums;

public enum Direction {
	NORTH (0, -1), 
	NORTHEAST (1, -1), 
	EAST (1, 0), 
	SOUTHEAST (1, 1), 
	SOUTH (0, 1), 
	SOUTHWEST (-1, 1), 
	WEST (-1, 0), 
	NORTHWEST (-1, -1);
	
	private final int adjX, adjY;
	Direction(int x, int y) {
		this.adjX = x;
		this.adjY = y;
	}
	
	public int adjX() {
		return this.adjX;
	}
	public int adjY() {
		return this.adjY;
	}
}
