package dungeonDigger.gameFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import dungeonDigger.Enums.AbilityDeliveryMethod;
import dungeonDigger.Enums.GameState;
import dungeonDigger.Tools.References;
import dungeonDigger.entities.Ability;
import dungeonDigger.entities.Mob;
import dungeonDigger.entities.NetworkPlayer;
import dungeonDigger.entities.templates.TypeTemplate;
import dungeonDigger.network.ConnectionState;

/**
 * Initial game state that loads most assets and maintains references to universal objects
 * @author Eric
 */
public class DungeonDigger extends StateBasedGame {
	
	/*Load all .csf files into memory for players' characters */
	public static void loadCharacterFiles() {
		BufferedReader in;
		File file = new File("data/characters");
		
		if( !file.isDirectory() ) { file.mkdir(); }
		else {
			// Create filter to ignore all but csf files
			FilenameFilter charFilesOnly = new FilenameFilter() {				
			};
			// Try to load each player file
			for( File f : file.listFiles( charFilesOnly ) ){
				try {
					in = new BufferedReader(new FileReader(f));
					NetworkPlayer loadee = new NetworkPlayer();
					String line = in.readLine();
					boolean duplicant = false;
					
					if( !line.equalsIgnoreCase("[CHARACTER]") ) {
						Logger.getAnonymousLogger().info("Character file: " + f.getName() + " seems corrupt. Skipping.");
						continue;
					}
					
					// Setup player object
					StringBuffer property = new StringBuffer();
					while( (line = in.readLine()) != null ) {
						property.append(line.substring(1, line.indexOf("]")));
						if( property.toString().equalsIgnoreCase("NAME") ) { 
							loadee.setName( line.substring(line.indexOf("]")+1));
							if( References.CHARACTERBANK.get(property) != null ) {
								Logger.getAnonymousLogger().info("Duplicant character found: " + loadee.getName());
								duplicant = true;
								break;
							}
						}
						if( property.toString().equalsIgnoreCase("XCOORD") ) { loadee.getPosition().x = Integer.valueOf(line.substring(line.indexOf("]")+1)); }
						if( property.toString().equalsIgnoreCase("YCOORD") ) { loadee.getPosition().y = Integer.valueOf(line.substring(line.indexOf("]")+1)); }
						if( property.toString().equalsIgnoreCase("MAXHITPOINTS") ) { loadee.setHitPoints( Integer.valueOf(line.substring(line.indexOf("]")+1))); }
						if( property.toString().equalsIgnoreCase("SPEED") ) { loadee.setSpeed( Integer.valueOf(line.substring(line.indexOf("]")+1))); }
						if( property.toString().equalsIgnoreCase("AVATAR") ) { loadee.setIconName( line.substring(line.indexOf("]")+1)); }
						property.setLength(0);
					}
					
					if( !duplicant ) {
						References.CHARACTERBANK.put(loadee.getName(), loadee);
						Logger.getAnonymousLogger().info("Archived character: " + loadee.getName());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void loadAbilities() {
		BufferedReader in;
		File file = new File("data/abilities");
		Ability templater = null;
		
		if( !file.isDirectory() ) { 
			file.mkdir();
			System.out.println("NO ABILITY FILES FOUND! PLEASE REINSTALL.");
			System.exit(-1);
		} else {
			FilenameFilter abilFilesOnly = new FilenameFilter() {				
			};
			// Try to load each ability file
			for( File f : file.listFiles( abilFilesOnly ) ){
				try {
					in = new BufferedReader(new FileReader(f));
					String line = in.readLine();
					String str1, str2, str3, lineValue;
					float x, y, animSpeed = 1, sAnimSpeed = 1;
					int x1=0, x2=0, y1=0, y2=0, sx1=0, sx2=0, sy1=0, sy2=0;
					StringBuffer lineName = new StringBuffer();
					boolean duplicant = false;
					
					if( !line.equalsIgnoreCase("[ABILITY]") ) {
						Logger.getAnonymousLogger().info("Ability file: " + f.getName() + " seems corrupt. Skipping.");
						continue;
					}
					
					// Setup ability object
					while( (line = in.readLine()) != null ) {
						lineValue = line.substring(line.indexOf("]")+1);
						lineName.append(line.substring(1, line.indexOf("]")));
						
						
						if( lineName.toString().equalsIgnoreCase("NAME") ) { 
							templater = new Ability(lineValue);
							if( References.ABILITY_TEMPLATES.get(templater.getName()) != null ) {
								Logger.getAnonymousLogger().info("Duplicant ability template found: " + templater.getName());
								duplicant = true;
								break;
							}
						}
						if( lineName.toString().equalsIgnoreCase("SPRITESHEET") ) { 
							str1 = lineValue;
							str2 = str1.substring( str1.lastIndexOf('_')+1, str1.lastIndexOf('x'));
							str3 = str1.substring(str1.lastIndexOf('x')+1, str1.lastIndexOf('.'));
							templater.setSpriteSheet( new SpriteSheet(new Image(str1, Color.magenta), Integer.parseInt(str2), Integer.parseInt(str3))); 
						}
						if( lineName.toString().equalsIgnoreCase("ANIMSTARTX") ) { x1 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("ANIMSTARTY") ) { y1 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("ANIMENDX") ) { x2 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("ANIMENDY") ) { y2 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("ANIMSPEED") ) { animSpeed = Float.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("SECONDARYANIMSTARTX") ) { sx1 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("SECONDARYANIMSTARTY") ) { sy1 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("SECONDARYANIMENDX") ) { sx2 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("SECONDARYANIMENDY") ) { sy2 = Integer.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("SECONDARYANIMSPEED") ) { sAnimSpeed = Float.valueOf(lineValue); }
						if( lineName.toString().equalsIgnoreCase("DAMAGING") ) { templater.setDamaging( Boolean.valueOf(lineValue)); }
						if( lineName.toString().equalsIgnoreCase("HITFRAMES") ) { 
							str1 = lineValue;
							str2 = str1.substring( str1.lastIndexOf('_')+1, str1.lastIndexOf('x'));
							str3 = str1.substring(str1.lastIndexOf('x')+1, str1.lastIndexOf('.'));
							templater.setHitFrames( new SpriteSheet(new Image(str1, Color.magenta), Integer.parseInt(str2), Integer.parseInt(str3))); 
						}
						if( lineName.toString().equalsIgnoreCase("SPEED") ) { templater.setSpeed( Integer.valueOf(lineValue)); }
						if( lineName.toString().equalsIgnoreCase("FRIENDLY") ) { templater.setFriendly( Boolean.valueOf(lineValue)); }
						if( lineName.toString().equalsIgnoreCase("MOUSE") ) { templater.setMouse( Boolean.valueOf(lineValue)); }
						if( lineName.toString().equalsIgnoreCase("START") ) { 
							if( !line.substring(line.indexOf(']')+1, line.indexOf(',')).equalsIgnoreCase("M") ) {
								x = Float.valueOf(line.substring(line.indexOf(']')+1, line.indexOf(',')));
								y = Float.valueOf(line.substring(line.indexOf(',')+1));
								templater.setStartPoint(x, y);
							}
						}
						if( lineName.toString().equalsIgnoreCase("MIDDLE") ) { 
							x = Float.valueOf(line.substring(line.indexOf(']')+1, line.indexOf(',')));
							y = Float.valueOf(line.substring(line.indexOf(',')+1));
							templater.setMiddlePoint(x, y); 
						}
						if( lineName.toString().equalsIgnoreCase("END") ) { 
							x = Float.valueOf(line.substring(line.indexOf(']')+1, line.indexOf(',')));
							y = Float.valueOf(line.substring(line.indexOf(',')+1));
							templater.setEndPoint(x, y); 
						}
						if( lineName.toString().equalsIgnoreCase("DELIVERY") ) { 
							String adm =lineValue;
							templater.setDeliveryMethod(AbilityDeliveryMethod.valueOf(adm));
						}
						lineName.setLength(0);
					}
					// Create animation from info
					templater.setAnimation(new Animation(templater.getSpriteSheet(), x1, y1, x2, y2, true, 100, false));
					templater.setSecondaryAnimation(new Animation(templater.getSpriteSheet(), sx1, sy1, sx2, sy2, true, 100, false));
					templater.getAnimation().setSpeed(animSpeed);	
					templater.getSecondaryAnimation().setSpeed(sAnimSpeed);	
					templater.getSecondaryAnimation().setLooping(false);	
					
					if( !duplicant ) {
						References.ABILITY_TEMPLATES.put(templater.getName(), templater);
						Logger.getAnonymousLogger().info("Archived ability template: " + templater.getName());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch( NumberFormatException e ) {
					e.printStackTrace();
				} catch( SlickException e ) {
					e.printStackTrace();
				}
			}
		}
		References.ABILITY_FACTORY.init();
	}
	
	public static void loadMobs() {
		BufferedReader in;
		File file = new File("data/mobs");
		Mob templater = null;
		
		if( !file.isDirectory() ) { 
			file.mkdir();
			System.out.println("NO MOB FILES FOUND! PLEASE REINSTALL.");
			System.exit(-1);
		} else {
			FilenameFilter mobFilesOnly = new FilenameFilter() {				
			};
			// Try to load each mob file
			for( File f : file.listFiles( mobFilesOnly ) ){
				try {
					in = new BufferedReader(new FileReader(f));
					String line = in.readLine();
					String str1, str2, str3;
					int x1=0, x2=0, y1=0, y2=0;
					StringBuffer property = new StringBuffer();
					boolean duplicant = false;
					
					if( !line.equalsIgnoreCase("[MOB]") ) {
						Logger.getAnonymousLogger().info("Mob file: " + f.getName() + " seems corrupt. Skipping.");
						continue;
					}
					
					// Setup mob object
					while( (line = in.readLine()) != null ) {
						property.append(line.substring(1, line.indexOf("]")));
						if( property.toString().equalsIgnoreCase("NAME") ) { 
							templater = new Mob(line.substring(line.indexOf("]")+1));
							if( References.MOB_TEMPLATES.get(templater.getName()) != null ) {
								Logger.getAnonymousLogger().info("Duplicant Mob template found: " + templater.getName());
								duplicant = true;
								break;
							}
						}
						if( property.toString().equalsIgnoreCase("SPRITESHEET") ) { 
							str1 = line.substring(line.indexOf("]")+1);
							str2 = str1.substring( str1.lastIndexOf('_')+1, str1.lastIndexOf('x'));
							str3 = str1.substring(str1.lastIndexOf('x')+1, str1.lastIndexOf('.'));
							templater.setSprites( new SpriteSheet(new Image(str1, Color.magenta), Integer.parseInt(str2), Integer.parseInt(str3))); 
						}
						if( property.toString().equalsIgnoreCase("ANIMSTARTX") ) { x1 = Integer.valueOf(line.substring(line.indexOf(']')+1)); }
						if( property.toString().equalsIgnoreCase("ANIMSTARTY") ) { y1 = Integer.valueOf(line.substring(line.indexOf(']')+1)); }
						if( property.toString().equalsIgnoreCase("ANIMENDX") ) { x2 = Integer.valueOf(line.substring(line.indexOf(']')+1)); }
						if( property.toString().equalsIgnoreCase("ANIMENDY") ) { y2 = Integer.valueOf(line.substring(line.indexOf(']')+1)); }
						if( property.toString().equalsIgnoreCase("SPEED") ) { templater.setSpeed( Integer.valueOf(line.substring(line.indexOf("]")+1))); }
						if( property.toString().equalsIgnoreCase("FRIENDLY") ) { templater.setFriendly( Boolean.valueOf(line.substring(line.indexOf("]")+1))); }
						if( property.toString().equalsIgnoreCase("HITPOINTS") ) { 
							templater.setMaxHitPoints( Integer.valueOf(line.substring(line.indexOf("]")+1))); 
							templater.setCurrentHitPoints( Integer.valueOf(line.substring(line.indexOf("]")+1))); 
						}
						if( property.toString().equalsIgnoreCase("TYPE") ) { 
							try {
								String type = line.substring(line.indexOf("]")+1);
								type = type.toLowerCase();
								type = type.replaceFirst(type.substring(0, 1), type.substring(0, 1).toUpperCase());
								Class template = Class.forName("dungeonDigger.entities.templates."+type+"Template");
								templater.setTypeTemplate((TypeTemplate)template.newInstance(), false);
							} catch(ClassNotFoundException e) {
								e.printStackTrace();
							} catch( InstantiationException e ) {
								e.printStackTrace();
							} catch( IllegalAccessException e ) {
								e.printStackTrace();
							}
						}
						property.setLength(0);
					}
					// Create animation from info
					templater.setAnimation(new Animation(templater.getSprites(), x1, y1, x2, y2, true, 100, false));
						
					
					if( !duplicant ) {
						References.MOB_TEMPLATES.put(templater.getName(), templater);
						Logger.getAnonymousLogger().info("Archived mob template: " + templater.getName());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch( NumberFormatException e ) {
					e.printStackTrace();
				} catch( SlickException e ) {
					e.printStackTrace();
				}
			}
		}
		References.MOB_FACTORY.init();
	}
}