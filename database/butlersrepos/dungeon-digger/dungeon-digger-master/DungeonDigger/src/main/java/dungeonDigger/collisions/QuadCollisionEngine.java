package dungeonDigger.collisions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;

import org.newdawn.slick.geom.Rectangle;

import dungeonDigger.Tools.Constants;
import dungeonDigger.Tools.References;
import dungeonDigger.contentGeneration.DungeonGenerator;
import dungeonDigger.entities.GameObject;
import dungeonDigger.entities.Mob;

public class QuadCollisionEngine {
	private ArrayList<QuadCollisionEngine>	children	= new ArrayList<QuadCollisionEngine>();
	private ArrayList<GameObject>			list		= new ArrayList<GameObject>();
	private QuadCollisionEngine				parent		= null;
	private int								tier		= 0;
	private static int						loopStop	= 0;
	// These responsibility measurements are in CELLS not pixels. Multiplying by the ratioRow/ratioCol gives the pixels.
	private int								responsibleX, responsibleY, responsibleWidth, responsibleHeight, breakingPoint;
	private boolean							isSplit		= false;
	private static QuadCollisionEngine 		NODE_ZERO	= null;
}
