package stuv7cb.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7814866059557571520L;
	private JButton clean=new JButton("Очистить");
	private MainPanel mainPanel =new MainPanel();
	private JPanel panel;
	private SpringLayout springLayout;
	private final String TITLE="Оптическая лаборатория";
}
