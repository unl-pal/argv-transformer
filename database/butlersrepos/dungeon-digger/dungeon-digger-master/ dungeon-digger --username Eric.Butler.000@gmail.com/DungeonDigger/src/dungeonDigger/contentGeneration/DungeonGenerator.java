package dungeonDigger.contentGeneration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.ResourceLoader;

import dungeonDigger.entities.NetworkPlayer;
import dungeonDigger.enums.BorderCheck;
import dungeonDigger.enums.Direction;
import dungeonDigger.gameFlow.DungeonDigger;
import dungeonDigger.network.Network;
import dungeonDigger.network.Network.GameStartPacket;

public class DungeonGenerator {
	public GameSquare[][] dungeon;
	public Vector<NetworkPlayer> playerList = new Vector<NetworkPlayer>();
	
	private int dungeonHeight = 10, dungeonWidth = 10, ratio = 5;
	private Random r = new Random(System.currentTimeMillis());
	private Vector<Room> roomList = new Vector<Room>();
	private HashMap<Integer, Room> roomDefinitionMap = new HashMap<Integer, Room>();
	private boolean isInitialized = false;	
	private Image roomWallImage, dirtFloorImage, roomFloorImage, dirtWallImage, entranceImage;
	private static final int ratioX = 99;
	private static final int ratioY = 82;
	private Vector2f entrance;
	
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
	public void generateDungeon2(int height, int width, int iterations, int neighbors, double closedness, boolean specifier) {
		this.initializeDungeon(height, width);
		// RNG the whole map
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(  r.nextDouble() < closedness ) {
					this.dungeon[y][x].setTileLetter('W');
				} else {
					this.dungeon[y][x].setTileLetter('O');
				}
			}
		}
		
		for(int n = 0; n < iterations; n++) {
			int w = r.nextInt(width);
			int h = r.nextInt(height);
			
			if (wallNeighbors(h, w) > neighbors) {
            	this.dungeon[h][w].setTileLetter(specifier ? 'W' : 'O');
            }
            else {
            	this.dungeon[h][w].setTileLetter(specifier ? 'O' : 'W');
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
		
		// Iterate over every room
		for( Room startRoom : this.roomList ) {
			if( r.nextDouble() >= chance ) { continue; }
			startedHalls++;
			// Grab a random room that's not this one
			do {
				i = r.nextInt(roomList.size());
			} while( roomList.get(i) == startRoom );
			
			// Find the distance vectors and directionality
			Room destRoom = roomList.get(i);
			
			// Add some variance to where on the border of the room we start from and goto
			startX = startRoom.getColumn() + r.nextInt( startRoom.getWidth() );
			startY = startRoom.getRow() + r.nextInt( startRoom.getHeight() );
			destX = destRoom.getColumn() + r.nextInt( destRoom.getWidth() );
			destY = destRoom.getRow() + r.nextInt( destRoom.getHeight() );
			
			diffX = destX - startX;
			diffY = destY - startY;
			
			// Pick a random axis to begin from 0-vertical, 1-horizontal
			startDirection = r.nextInt(2);
			
			// Loop to draw the hallway using the Marked flag of subsequent squares as it "digs"
			// That way we can roll it back if we need to, otherwise process them
			int currentRow, currentColumn;
			Hallway currentHallway = new Hallway();
			
			digHallway:	//Loop label for the digging of the current hallway
			for(int n = 1; n < Math.abs(diffX) + Math.abs(diffY); n++) {					
				// This IF block makes our "cursor" follow a 90degree path from start to dest
				if( startDirection == 0 ) {
					// Going vertically first
					currentRow = (int) (startY + Math.min( Math.abs(diffY), n) * Math.signum((double)diffY));			
					currentColumn = (int) (startX + Math.max( n - Math.abs(diffY), 0) * Math.signum((double)diffX));	
				} else {
					// Going horizontally first
					currentColumn = (int) (startX + Math.min( Math.abs(diffX), n) * Math.signum((double)diffX));		
					currentRow = (int) (startY + Math.max(n - Math.abs(diffX), 0) * Math.signum((double)diffY));		
				}
				
				
				GameSquare currentSquare = this.dungeon[currentRow][currentColumn];
				
				// If we're still in the startRoom, move on
				if( currentSquare.getBelongsTo() == startRoom ) { 
					continue; 
				} else {
					// Check our orthogonal neighbors
					for(int y = -1; y < 2; y++) {
						for(int x = -1; x < 2; x++) {
							// Guarantees orthogonal checks only, no diagnols
							if( y != 0 && x != 0 ) { continue; }
							// Keeps us in bounds
							if( currentRow+x < 0 || currentRow+x >= this.dungeonHeight 
									|| currentColumn+y < 0 || currentColumn+y >= this.dungeonWidth ) {
								continue;
							}
							GameSquare inspectee = this.dungeon[currentRow+y][currentColumn+x];
							// Checks owners to determine our actions, stop if its not startRoom or our hallway
							if( inspectee.getTileLetter() == 'O' &&
									inspectee.getBelongsTo() != startRoom &&
									inspectee.getBelongsTo() != currentHallway) {
								// If we're still next to the start room, only claim if the triggering square is opposite from the startRoom
								// otherwise the triggering hall is probably connecting to the startRoom
								if( checkBordersOwners(currentRow, currentColumn, BorderCheck.ORTHOGONAL, startRoom).size() > 0) {
									if( this.dungeon[currentRow+y*-1][currentColumn+x*-1].getBelongsTo() == startRoom ) {
										currentSquare.setTileLetter('O');
										currentSquare.setBelongsTo(currentHallway);
									}
								} else {
									currentSquare.setTileLetter('O');
									currentSquare.setBelongsTo(currentHallway);
								}
								halls++;
								break digHallway;
							}
						}
					}
					currentSquare.setTileLetter('O');
					currentSquare.setBelongsTo(currentHallway);
				}
			}
		}
	}
	
	/**
	 * Returns true if room was placed successfully
	 * @param roomID
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean placeRoom( int roomID, int row, int column ) {
		// Failsafes
		if( row < 0 ) { return false; }
		if( column < 0 ) { return false; }
		if( roomDefinitionMap.get(roomID).getWidth() + column > this.dungeonWidth ) { return false; }
		if( roomDefinitionMap.get(roomID).getHeight() + row > this.dungeonHeight ) { return false; }
		
		// Make sure the area is clear for the room
		for(int i = -1; i <= roomDefinitionMap.get(roomID).getWidth(); i++) {
			for(int n = -1; n <= roomDefinitionMap.get(roomID).getHeight(); n++) {
				if( !checkCell(row+n, column+i) ) { 
					return false; 
				}
			}
		}
		
		Room newRoom = roomDefinitionMap.get(roomID).copy();
		newRoom.setRow(row);
		newRoom.setColumn(column);
		roomList.add( newRoom );
		// Actually place the room
		// TODO: apply real properties instead of just an 'O'
		for(int i = 0;i < roomDefinitionMap.get(roomID).getWidth(); i++) {
			for(int n = 0;n < roomDefinitionMap.get(roomID).getHeight(); n++) {
				this.dungeon[row+n][column+i].setTileLetter('O');
				this.dungeon[row+n][column+i].setBelongsTo(newRoom);
			}
		}
		
		return true;
	}
	
	private double getDungeonDensity() {
		double result  = 0;
		
		for(int i = 0; i < this.dungeonWidth; i++) {
			for(int n = 0; n < this.dungeonHeight; n++) {
				if( this.dungeon[n][i].getTileLetter() == 'O' ) {
					result++;
				}
			}			
		}
		return result / (this.dungeonHeight * this.dungeonWidth);
	}
}
