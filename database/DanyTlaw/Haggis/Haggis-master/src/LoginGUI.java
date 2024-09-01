import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.glass.events.WindowEvent;


public class LoginGUI extends JFrame implements ActionListener{

	ObjectOutputStream out;
	ObjectInputStream in;
	
	private String name;
	
	private JButton jbtStart;
	private JTextField txtName;
	private JTextField txtPunkte;
	
	private JLabel background;
	private JLabel lblName;
	private JLabel lblPunkte;
	private JLabel lblHolder1;
	private JLabel lblHolder2;
	private JLabel lblHolder3;
	private JLabel lblHolder4;
	
	
	
	public String pfad = System.getProperty("user.dir") + "//images//";
	public Spieltisch tisch;
	
	
	
	
}
