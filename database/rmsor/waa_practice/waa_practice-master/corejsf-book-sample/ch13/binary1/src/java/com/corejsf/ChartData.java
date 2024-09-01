package com.corejsf;

import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartData implements BinaryData {   
   private int width, height;
   private String title;
   private String[] names;
   private double[] values;

   private static final int DEFAULT_WIDTH = 200;
   private static final int DEFAULT_HEIGHT = 200;
}