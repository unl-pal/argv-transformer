package com.corejsf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

public class ChartData implements BinaryData {   
   private int width, height;
   private String title;
   private String[] names;
   private double[] values;

   private static final int DEFAULT_WIDTH = 200;
   private static final int DEFAULT_HEIGHT = 200;   
}