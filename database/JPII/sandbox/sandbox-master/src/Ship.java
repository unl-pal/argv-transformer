import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Ship {
	
	protected int x,y;
	protected int size;
	protected boolean moving;
	protected int color;
	protected double angle;
	
	public final int NORTH = 0;
	public final int SOUTH = 180;
	public final int EAST = 270;
	public final int WEST = 90;
}
