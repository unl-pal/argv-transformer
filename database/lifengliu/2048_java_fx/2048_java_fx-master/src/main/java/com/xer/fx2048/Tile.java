/*
* The class for a tile
 */
package com.xer.fx2048;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 *
 * @author lifeng liu 
 */
public class Tile {
	private int x;
	private int y;
	private GamePane pane;
	private Color color;
	private int value;
	private Rectangle rect;
	private Text text;
	public final static int TILEWIDTH=60;
	public final static int TILEHEIGHT=60;
	
}
