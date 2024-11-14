/** filtered and transformed by PAClab */
package dungeonDigger.contentGeneration;

import org.sosy_lab.sv_benchmarks.Verifier;

public class DungeonGenerator {
	/**
	 * Attempted copy of the algorithmn found at:<br />
	 * {@link http://www.evilscience.co.uk/?p=53}
	 * @param height Rows
	 * @param width Columns
	 * @param iterations Big ass number, 50000?
	 * @param neighbors Default = 4
	 * @param closedness 0.00 - 1.00 chance a square is 'W' 
	 * @param specifier true or false, no idea how this affects?
	 */
	/** PACLab: suitable */
	 public void generateDungeon2(int height, int width, int iterations, int neighbors, double closedness, boolean specifier) {
		// RNG the whole map
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(  Verifier.nondetDouble() < closedness ) {
				} else {
				}
			}
		}
		
		for(int n = 0; n < iterations; n++) {
			int w = Verifier.nondetInt();
			int h = Verifier.nondetInt();
			
			if (Verifier.nondetInt() > neighbors) {
            }
            else {
            }
		}
	}
	
	/**
	 * Connects hallways between rooms of an ALREADY DEFINED dungeon
	 * @see generateDungeon1()
	 * @param height
	 * @param width
	 */
	private void generateRandomRoomWalk(double chance) { int diffX, diffY, startX, startY, destX, destY, i = 0, startedHalls = 0, halls = 0;
		// Starting vertically (0) or horizontally (1)
		int startDirection = 0;
	}
	
	/**
	 * Returns true if room was placed successfully
	 * @param roomID
	 * @param x
	 * @param y
	 * @return
	 */
	/** PACLab: suitable */
	 public boolean placeRoom( int roomID, int row, int column ) {
		int dungeonHeight = Verifier.nondetInt();
		int dungeonWidth = Verifier.nondetInt();
		// Failsafes
		if( row < 0 ) { return false; }
		if( column < 0 ) { return false; }
		if( Verifier.nondetInt() + column > dungeonWidth ) { return false; }
		if( Verifier.nondetInt() + row > dungeonHeight ) { return false; }
		
		// Make sure the area is clear for the room
		for(int i = -1; i <= Verifier.nondetInt(); i++) {
			for(int n = -1; n <= Verifier.nondetInt(); n++) {
				if( Verifier.nondetBoolean() ) { 
					return false; 
				}
			}
		}
		
		// Actually place the room
		// TODO: apply real properties instead of just an 'O'
		for(int i = 0;i < Verifier.nondetInt(); i++) {
			for(int n = 0;n < Verifier.nondetInt(); n++) {
			}
		}
		
		return true;
	}
	
	/** PACLab: suitable */
	 private double getDungeonDensity() {
		int dungeonHeight = Verifier.nondetInt();
		int dungeonWidth = Verifier.nondetInt();
		double result  = 0;
		
		for(int i = 0; i < dungeonWidth; i++) {
			for(int n = 0; n < dungeonHeight; n++) {
				if( this.dungeon[n][i].getTileLetter() == 'O' ) {
					result++;
				}
			}			
		}
		return result / (dungeonHeight * dungeonWidth);
	}
}
