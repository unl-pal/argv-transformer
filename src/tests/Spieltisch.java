package tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;














import javax.swing.border.TitledBorder;

import com.sun.glass.events.WindowEvent;

import java.lang.*;

public class Spieltisch extends JFrame{

	private Image image;
	private ImageIcon icon;
	 
	//Diese Variable ist von Anfang and true wenn ein neuer Client geoeffnet wird
	private boolean neuGestartet = true;
	private boolean wette = false;
	
	int bubeAnzahl;
	int dameAnzahl;
	int koenigAnzahl;
	int handkarten;
	int haggisAnzahl;
	
	//public Color background = new Color(255,255,255);
	String[] spielKarten = new String[14];
	
    int kartenBound = 0;
    int jokerKartenBound = 0;
	
	public JButton[] btnKarte = new JButton[14];
	public JButton[] jokerKarten = new JButton[3];
	public JLabel[] anzeigeKarten = new JLabel[14];
	public Boolean[] gedrucktJoker = new Boolean[3];
	public Boolean[] gedrucktHand = new Boolean[14];
	
	//Buttons welche zum Spielen des Spiels gebraucht werden
	public JButton jbtSpielen;
	public JButton jbtPassen;
	public JButton jbtEingabe;
	public JButton jbtWetten;
	
	public ArrayList<Card> hand; 
	
	//ArrayListe fuer die Karten welche im SpielFeld sind
	public ArrayList<Card> feldKarten = new ArrayList<Card>();
	
	//ArrayListe fuer die Karten welche ausgepspielt werden
	public ArrayList<Card> gespielteKarten = new ArrayList<Card>();
	
	public String pfad = System.getProperty("user.dir") + "//images//";
	
	public String name;

	
	private Image imageRueckseite;
	private ImageIcon rueckseite;
	private Image jBube;
	private ImageIcon jBubeIcon;
	private Image jDame;
	private ImageIcon jDameIcon;
	private Image jKoenig;
	private ImageIcon jKoenigIcon;
	 
	
	private ImageIcon hintergrund;
	private Image imageHintergrund;
	
	
	static ObjectOutputStream out;
	static ObjectInputStream in;
	
	
	//ArrayList zum testen
	ArrayList<Card> test = new ArrayList<Card>();
	//Border erstellen
	public Border gedrucktBorder = new LineBorder(Color.BLUE, 2);
	
	private JLabel lblKoenig;
	private JLabel lblDame;
	private JLabel lblBube;
	
	public JLabel lblPunkteEigen;
	public JLabel lblPunkteGegner;
	public JLabel lblHandkarten;
	
	public JLabel lblInfo;
	
	//Chat
	private JTextArea txtAEingabe;
	private JTextArea txtAChat;
	public Chat chat;
	
	//Wetten
	public JTextField EigeneWetten;
	public JTextField GegnerWetten;
	
	public JLabel lblEigeneWetten;
	public JLabel lblGegnerWetten;
	
	
	JPanel haggis = new EigenPanel(4);
	public JLabel haggisKarten = new JLabel ("Haggis:");
	public TitledBorder Haggisborder = new TitledBorder(null,haggisKarten.getText(),TitledBorder.LEFT,TitledBorder.DEFAULT_POSITION, new Font("Arial",Font.BOLD, 12), Color.BLACK);

	
	private int kartenBreite = 60;
	private int kartenHoehe = 100;
	private int spielfeldHoehe = 110;
	private int spielfeldBreite = 545;
	private int rueckseiteBreite = 80;
	private int rueckseiteHoehe = 130;
	private int iconBreite = 60;
	private int iconHoehe = 60;
	private int linksStrBreite = 200;
	private int linksStrHoehe = 200;
	private int rechtsStrBreite = 500;
	private int rechtsStrHoehe = 240;
	private int eigeneKartenBreite = 500;
	private int eigeneKartenHoehe = 110;
	
	
	public Spieltisch(ObjectOutputStream out, ObjectInputStream in, String name){
		
		this.name = name;
		Spieltisch.out = out;
		Spieltisch.in= in;
		
		
		clickHandler cHandler = new clickHandler();
		buttonHandler bHandler = new buttonHandler();
		
					
		JFrame Tisch = new JFrame("Haggis");
		
		//GrundPanel fuer die GUI	
		JPanel hauptPanel = new EigenPanel(4);
		hauptPanel.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.weightx = 0;
		cons.weighty = 0;
			
		//Variabel fuer die Aufloesung
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		 
		 //uberpruefung fuer aufloesung damit gui nicht zu gross
		if(height > 1000 && width > 1300){
			kartenBreite = 100;
			kartenHoehe = 150;
			spielfeldBreite = 825;
			spielfeldHoehe = 170;
			iconBreite = 150;
			iconHoehe = 150;			
			linksStrBreite = 310;
			linksStrHoehe = 200; 
			rechtsStrBreite = 800;
			rechtsStrHoehe = 360;
			eigeneKartenBreite = 800;	
			eigeneKartenHoehe = 170;
			rueckseiteBreite = 150;
			rueckseiteHoehe = 200;
		
							 
		 }
		 
		//Border welche in der Gui verwendet werden
		LineBorder borderinvis = new LineBorder(Color.BLACK, 0, true);
		LineBorder border = new LineBorder(Color.BLACK, 3, true);
		TitledBorder Chatborder = new TitledBorder(border,"Chat",TitledBorder.LEFT,TitledBorder.DEFAULT_POSITION, new Font("Arial",Font.BOLD, 12), Color.BLACK);
		TitledBorder Handkartenborder = new TitledBorder(borderinvis,"Handkarten",TitledBorder.LEFT,TitledBorder.DEFAULT_POSITION, new Font("Arial",Font.BOLD, 12), Color.BLACK);
		TitledBorder Jokerkartenborder = new TitledBorder(borderinvis,"Jokerkarten",TitledBorder.LEFT,TitledBorder.DEFAULT_POSITION, new Font("Arial",Font.BOLD, 12), Color.BLACK);
		Haggisborder.setBorder(borderinvis);
		
				
		/***************************************************************************************
		NORTH TEIL DES HAUPTPANEL
		****************************************************************************************/
		
		//JPanel welche alle informationen ueber den Gegner beinhaltet
		//GrundPanel des Noerdlichen Teil
		JPanel enemy = new EigenPanel(4);
		enemy.setLayout(new BoxLayout(enemy, BoxLayout.X_AXIS));
		
		JPanel enemyKarten = new EigenPanel(4);
		enemyKarten.setLayout(new GridBagLayout());
		
		//Erstellt einen Image in einem ImageIcon welches einem Label hinzugefuegt wird
		image = new ImageIcon(pfad +"icon.png").getImage();
		icon = new ImageIcon(image.getScaledInstance(iconBreite, iconHoehe, Image.SCALE_DEFAULT));	
		JLabel lblImage = new JLabel();
		lblImage.setIcon(icon);
		
		//JLabels welche die Karten des Gegner anzeigen
		lblBube = new JLabel("Bube: " + bubeAnzahl);
		lblDame = new JLabel("Dame: " + dameAnzahl);
		lblKoenig = new JLabel("Koenig: " + koenigAnzahl);
		lblHandkarten = new JLabel("HandKarten: ");
			
		//Added alle Informationen dem JPanel enemyKarten
		cons.ipadx = 15;
		cons.ipady = 15;
		
		cons.gridy = 0;
		cons.gridx = 0;
		enemyKarten.add(lblBube, cons);
		
		cons.gridy = 0;
		cons.gridx = 1;
		enemyKarten.add(lblDame, cons);
		
		cons.gridy = 0;
		cons.gridx = 2;
		enemyKarten.add(lblKoenig, cons);
		
		cons.weightx = 0.0;
		cons.gridy = 1;
		cons.gridx = 0;
		cons.gridwidth = 3;
		enemyKarten.add(lblHandkarten, cons);
		
		//Panel fuer die anzeige der eigenen und gegnerischen Punkte
		JPanel PunkteInfo = new EigenPanel(4);
		PunkteInfo.setLayout(new GridBagLayout());
		
		lblPunkteEigen = new JLabel("Punkte: ");
		lblPunkteGegner = new JLabel("Gegnerische Punkte: ");
		
		//Added alle Info der Componente Punkte Info
		cons.gridy = 0;
		cons.gridx = 0;
		PunkteInfo.add(lblPunkteEigen, cons);
		
		cons.gridy = 1;
		cons.gridx = 0;
		PunkteInfo.add(lblPunkteGegner, cons);
		
		//Label schriftfarbe ändern
		lblBube.setForeground(Color.black);
		lblDame.setForeground(Color.black);
		lblKoenig.setForeground(Color.black);
		lblHandkarten.setForeground(Color.black);
		lblPunkteEigen.setForeground(Color.black);
		lblPunkteGegner.setForeground(Color.black);
		

		//GrundPanel fuer die Wetten
		JPanel WettenPanel = new EigenPanel(4);
		WettenPanel.setLayout(new BoxLayout(WettenPanel, BoxLayout.Y_AXIS));
		
		//Pane fuer die Eigene Wetten
		JPanel EigenWette = new EigenPanel(4);
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
		EigenWette.setLayout(flow);
		//Text feld zur EIngabe der eignenen Wette
		EigeneWetten = new JTextField(15);
		EigeneWetten.setEditable(false);
		EigeneWetten.addFocusListener(new FocusListener(){
		        @Override
		        public void focusGained(FocusEvent e){
		        	if(EigeneWetten.getText().equals("Platzieren Sie hier ihre Wette!")){
		        		EigeneWetten.setText("");
		        	}else{
		        		
		        	}
		        }
				@Override
				public void focusLost(FocusEvent e) {
					// TODO Auto-generated method stub
					if(EigeneWetten.getText().equals("")){
						EigeneWetten.setText("Platzieren Sie hier ihre Wette!");
					}
				}
		    });
		//Button um wette abzuschliessen
		jbtWetten = new JButton ("Wetten!");
		//Panel fuer die Gegnerwette
		JPanel GegnerWette = new EigenPanel(4);
		GegnerWette.setLayout(new FlowLayout(FlowLayout.LEFT));
		//Textfeld zur Anzeige der Gegner Wette
		GegnerWetten = new JTextField(20);
		GegnerWetten.setEditable(false);
			
		jbtWetten.addActionListener(bHandler);
		jbtWetten.setEnabled(false);
		
		EigenWette.add(EigeneWetten);
		EigenWette.add(Box.createRigidArea(new Dimension(55,0)));
		EigenWette.add(jbtWetten);
		
		GegnerWette.add(GegnerWetten);
	
		WettenPanel.add(EigenWette);
		WettenPanel.add(GegnerWette);
		
		//Added alle Componente dem JPanel enemy
		enemy.add(Box.createRigidArea(new Dimension(100,0)));
		enemy.add(enemyKarten);
		enemy.add(Box.createRigidArea(new Dimension(10,0)));
		enemy.add(lblImage);
		enemy.add(Box.createRigidArea(new Dimension(10,0)));
		enemy.add(PunkteInfo);
		
		cons.ipady = 0;
		cons.ipadx = 0;		
		
		/***************************************************************************************
		CENTER TEIL DES HAUPTPANEL
		****************************************************************************************/
		//Grundpanel fuer den Zentralen Teil
		JPanel spiel = new EigenPanel(4);
		spiel.setLayout(new FlowLayout());
		
		//Panel fuer das SPielfeld
		JPanel spielfeld = new EigenPanel(2);
		spielfeld.setLayout(new BoxLayout(spielfeld, BoxLayout.Y_AXIS));
		
		haggis.setLayout(new BoxLayout(haggis, BoxLayout.Y_AXIS));
				
		//Erstellt einen Image in einem ImageIcon welches einem Label hinzugefuegt wird
		imageRueckseite = new ImageIcon(pfad +"rueckseite.png").getImage();
		rueckseite = new ImageIcon(imageRueckseite.getScaledInstance(rueckseiteBreite, rueckseiteHoehe, Image.SCALE_DEFAULT));	
		JLabel lblHaggis = new JLabel();
		lblHaggis.setIcon(rueckseite);
			
		//JPanel fuer den Oberen Teil des Spielfeld
		JPanel spielfeldoben = new EigenPanel(2);
		spielfeldoben.setPreferredSize(new Dimension(spielfeldBreite, spielfeldHoehe));
		spielfeldoben.setMaximumSize(new Dimension(spielfeldBreite, spielfeldHoehe));
		spielfeldoben.setMinimumSize(new Dimension(spielfeldBreite, spielfeldHoehe));
		spielfeldoben.setLayout(new BoxLayout(spielfeldoben, BoxLayout.X_AXIS));

		//Schleife welches 7 Labels im oberen Spielfeld erstellt
		spielfeldoben.add(Box.createHorizontalStrut(30));
		for(int i = 0;i<7;i++){
			JLabel anzeigeKarte = new JLabel();
			anzeigeKarte.setPreferredSize(new Dimension(kartenBreite, kartenHoehe+5));
			anzeigeKarte.setMaximumSize(new Dimension(kartenBreite, kartenHoehe+5));
			anzeigeKarte.setMinimumSize(new Dimension(kartenBreite, kartenHoehe+5));
			anzeigeKarten[i] = anzeigeKarte;
			spielfeldoben.add(anzeigeKarten[i]);
			spielfeldoben.add(Box.createHorizontalStrut(5));
		}
		
		//JPanel fuer den unteren Teil des Spielfelds
		JPanel spielfeldunten = new EigenPanel(2);
		spielfeldunten.setPreferredSize(new Dimension(spielfeldBreite, spielfeldHoehe));
		spielfeldunten.setMaximumSize(new Dimension(spielfeldBreite, spielfeldHoehe));
		spielfeldunten.setMinimumSize(new Dimension(spielfeldBreite, spielfeldHoehe));
		spielfeldunten.setLayout(new BoxLayout(spielfeldunten, BoxLayout.X_AXIS));
					

				
		//Schleife welches 7 Labels im unteren Teil des Spielfelds erstellt
		spielfeldunten.add(Box.createHorizontalStrut(30));
		for(int i = 7;i<14;i++){
			JLabel anzeigeKarte = new JLabel();
			anzeigeKarte.setPreferredSize(new Dimension(kartenBreite, kartenHoehe+5));
			anzeigeKarte.setMaximumSize(new Dimension(kartenBreite, kartenHoehe+5));
			anzeigeKarte.setMinimumSize(new Dimension(kartenBreite, kartenHoehe+5));
			anzeigeKarten[i] = anzeigeKarte;
			spielfeldunten.add(anzeigeKarten[i]);
			spielfeldunten.add(Box.createHorizontalStrut(5));
		}
				
		haggis.add(lblHaggis);
		haggis.setBorder(Haggisborder);
		
		spielfeld.add(spielfeldoben);
		spielfeld.add(spielfeldunten);
		spielfeld.setBorder(border);
		
		spiel.add(haggis);
		spiel.add(spielfeld);
		
		
		/***************************************************************************************
		SOUTH TEIL DES HAUPTPANEL
		****************************************************************************************/
		//Grundpanel fuer den Suedlichen teil
		JPanel spielSteuerung = new EigenPanel(4);
		spielSteuerung.setLayout(new BoxLayout(spielSteuerung, BoxLayout.Y_AXIS));
		//Panel fuer die Karten
		JPanel kartenSteuerung = new EigenPanel(4);
		kartenSteuerung.setLayout(new BoxLayout(kartenSteuerung, BoxLayout.X_AXIS));
		//Linker Panel fuer die Karten (Joker)
		JPanel linksSteuerung = new EigenPanel(4);
		linksSteuerung.setLayout(new BoxLayout(linksSteuerung, BoxLayout.Y_AXIS));	
		linksSteuerung.setPreferredSize(new Dimension(linksStrBreite,linksStrHoehe));
		linksSteuerung.setMaximumSize(new Dimension(linksStrBreite,linksStrHoehe));
		linksSteuerung.setMinimumSize(new Dimension(linksStrBreite,linksStrHoehe));
		//Rechter Panel fuer die Karten (Handkarten)
		JPanel rechtsSteuerung = new EigenPanel(4);
		rechtsSteuerung.setLayout(new BoxLayout(rechtsSteuerung, BoxLayout.Y_AXIS));
		rechtsSteuerung.setPreferredSize(new Dimension(rechtsStrBreite,rechtsStrHoehe));
		rechtsSteuerung.setMaximumSize(new Dimension(rechtsStrBreite,rechtsStrHoehe));
		rechtsSteuerung.setMinimumSize(new Dimension(rechtsStrBreite,rechtsStrHoehe));
		
		//Alle Container fuer das  Panel jokerkarten werden gemacht		
		JPanel buttons = new EigenPanel(4);
		buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		jbtSpielen = new JButton("Spielen");
		jbtSpielen.addActionListener(bHandler);
		jbtPassen = new JButton("Passen");
		jbtPassen.addActionListener(bHandler);

		
		//Disabled die Buttons des Spieltisch
		this.jbtSpielen.setEnabled(false);
		this.jbtPassen.setEnabled(false);

		
		jbtSpielen.addActionListener(cHandler);
		buttons.add(jbtSpielen);
		buttons.setBorder(new EmptyBorder(0,0,0,10));
		buttons.add(jbtPassen);
		
		
		//Panel fuer die Handkarten ausser Joker
		JPanel eigeneKartenButtonsOben = new EigenPanel(4);
		eigeneKartenButtonsOben.setPreferredSize(new Dimension(eigeneKartenBreite, eigeneKartenHoehe));
		eigeneKartenButtonsOben.setMaximumSize(new Dimension(eigeneKartenBreite, eigeneKartenHoehe));
		eigeneKartenButtonsOben.setMinimumSize(new Dimension(eigeneKartenBreite, eigeneKartenHoehe));
		eigeneKartenButtonsOben.setLayout(new BoxLayout(eigeneKartenButtonsOben, BoxLayout.X_AXIS));
		
		//Generiert 7 Buttons und fuegt sie dem Array hinzu	
		for(int i = 0;i<7;i++){
			JButton jbtKarte = new JButton();
			jbtKarte.setPreferredSize(new Dimension(kartenBreite,kartenHoehe));
			jbtKarte.setMaximumSize(new Dimension(kartenBreite,kartenHoehe));
			jbtKarte.setMinimumSize(new Dimension(kartenBreite,kartenHoehe));
			jbtKarte.setBounds(kartenBound, 5, 60, 100);
			btnKarte[i] = jbtKarte;
			btnKarte[i].addActionListener(cHandler);
			gedrucktHand[i] = false;
			eigeneKartenButtonsOben.add(btnKarte[i]);
			kartenBound+=kartenBreite;
		}
		//Panel fuer die Handkarten ausser Joker
		JPanel eigeneKartenButtonsUnten = new EigenPanel(4);
		eigeneKartenButtonsUnten.setPreferredSize(new Dimension(eigeneKartenBreite, eigeneKartenHoehe));
		eigeneKartenButtonsUnten.setMaximumSize(new Dimension(eigeneKartenBreite, eigeneKartenHoehe));
		eigeneKartenButtonsUnten.setMinimumSize(new Dimension(eigeneKartenBreite, eigeneKartenHoehe));
		eigeneKartenButtonsUnten.setLayout(new BoxLayout(eigeneKartenButtonsUnten, BoxLayout.X_AXIS));
		
		//Generiert 7 Buttons und fuegt sie dem Array hinzu	
		for(int i = 7;i<14;i++){
			kartenBound = 0;
			JButton jbtKarte = new JButton();
			jbtKarte.setPreferredSize(new Dimension(kartenBreite,kartenHoehe));
			jbtKarte.setMaximumSize(new Dimension(kartenBreite,kartenHoehe));
			jbtKarte.setMinimumSize(new Dimension(kartenBreite,kartenHoehe));
			jbtKarte.setBounds(kartenBound, 5, 60, 100);
			btnKarte[i] = jbtKarte;
			btnKarte[i].addActionListener(cHandler);
			gedrucktHand[i] = false;
			eigeneKartenButtonsUnten.add(btnKarte[i]);
			kartenBound+=kartenBreite;
		}
		//Panel fuer die Jokerkarten
		JPanel jokerkarten = new EigenPanel(4);
		jokerkarten.setLayout(new BoxLayout(jokerkarten, BoxLayout.X_AXIS));
		
		for(int i = 0; i<3;i++){
			JButton jokerKarte = new JButton();
			jokerKarte.setPreferredSize(new Dimension(kartenBreite,kartenHoehe));
			jokerKarte.setMaximumSize(new Dimension(kartenBreite,kartenHoehe));
			jokerKarte.setMinimumSize(new Dimension(kartenBreite,kartenHoehe));
			jokerKarte.setBounds(jokerKartenBound, 5 ,60,100);
			jokerKarten[i] = jokerKarte;
			jokerKarten[i].addActionListener(cHandler);
			gedrucktJoker[i] = false;
			jokerkarten.add(jokerKarten[i]);
			jokerKartenBound+=kartenBreite;
			
		}
		
		rechtsSteuerung.add(eigeneKartenButtonsOben);
		rechtsSteuerung.add(eigeneKartenButtonsUnten);
		rechtsSteuerung.setBorder(Handkartenborder);
		
		
		linksSteuerung.add(jokerkarten);
		linksSteuerung.add(buttons);
		linksSteuerung.setBorder(Jokerkartenborder);
		
		kartenSteuerung.add(linksSteuerung);
		kartenSteuerung.add(Box.createRigidArea(new Dimension(50,0)));
		kartenSteuerung.add(rechtsSteuerung);
		
		spielSteuerung.add(kartenSteuerung);
		
		/***************************************************************************************
		CHAT / Status Panel
		****************************************************************************************/
		
		//Chat		
		//Panel fuer den gesamten Chat
		JPanel chatPanel = new EigenPanel(4);
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		
		//Panel fuer die Eingabe des Chat (Textfeld/Button)
		JPanel chatEingabe = new EigenPanel(4);
		chatEingabe.setLayout(new BoxLayout(chatEingabe, BoxLayout.X_AXIS));
		
		//Button zum Senden des Chats
		jbtEingabe = new JButton("Eingabe");
		jbtEingabe.setEnabled(false);
		jbtEingabe.addActionListener(bHandler);
		
		//Textfeld welcher Chat darstellt
		txtAChat = new JTextArea(20,20);
		txtAChat.setLineWrap(true);
	    txtAChat.setEditable(false);
	  	txtAChat.setVisible(true);
		txtAChat.setBorder(Chatborder);
		
		//Scrollpane fuer den Chat
		JScrollPane scroll = new JScrollPane(txtAChat);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Textfeld fuer die Eingabe
		txtAEingabe = new JTextArea(2,1);
		txtAEingabe.setLineWrap(true);
		txtAEingabe.setEditable(false);
		
		//Scrollpane fuer die Eingabe
		JScrollPane scrollEingabe = new JScrollPane(txtAEingabe);
		scrollEingabe.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		chatEingabe.add(scrollEingabe);
		chatEingabe.add(jbtEingabe);
		
		chatPanel.add(scroll);
		chatPanel.add(Box.createRigidArea(new Dimension(0,10)));
		chatPanel.add(chatEingabe);
		
		
		//Panel für Statusmeldung
		JPanel lblHolder = new EigenPanel(3);
		lblHolder.setLayout(new FlowLayout(FlowLayout.CENTER));
		lblHolder.setPreferredSize(new Dimension(400,100));
		lblHolder.setMaximumSize(new Dimension(400, 100));
		lblHolder.setMinimumSize(new Dimension(400, 100));
		lblHolder.setBorder(border);
		
		lblInfo = new JLabel("Warten auf Gegner!");
		lblInfo.setFont(new Font("Verdana", Font.BOLD, 15));
		lblInfo.setForeground(Color.black);
	
		
		lblHolder.add(lblInfo);
				
		//Alles dem hauptPanel adden
		cons.weightx=0;
		cons.weighty=0;
		cons.gridwidth = 1;
		
		
		cons.gridx = 0;
		cons.gridy = 0;
		hauptPanel.add(enemy, cons);
		
		cons.gridx = 0;
		cons.gridy = 1;
		hauptPanel.add(spiel, cons);
		
		cons.gridx = 0;
		cons.gridy = 2;
		cons.gridwidth = 1;
		hauptPanel.add(spielSteuerung,cons);
		
		cons.gridx = 1;
		cons.gridy = 0;
		hauptPanel.add(WettenPanel,cons);
		
		cons.gridx = 1;
		cons.gridy = 1;
		hauptPanel.add(lblHolder,cons);
				
		cons.gridx = 1;
		cons.gridy = 2;
		hauptPanel.add(chatPanel, cons);
		
		
		//Alle Panels transparent
		PunkteInfo.setBackground(new Color(0,0,0,1));
		enemy.setBackground(new Color(0,0,0,1));
		enemyKarten.setBackground(new Color(0,0,0,1));
		spiel.setBackground(new Color(0,0,0,1));
		haggis.setBackground(new Color(0,0,0,1));
		spielfeld.setBackground(new Color(0,0,0,1));
		spielfeldoben.setBackground(new Color(0,0,0,1));
		spielfeldunten.setBackground(new Color(0,0,0,1));
		spielSteuerung.setBackground(new Color(0,0,0,1));
		kartenSteuerung.setBackground(new Color(0,0,0,1));
		linksSteuerung.setBackground(new Color(0,0,0,1));
		rechtsSteuerung.setBackground(new Color(0,0,0,1));
		buttons.setBackground(new Color(0,0,0,1));
		eigeneKartenButtonsOben.setBackground(new Color(0,0,0,1));
		eigeneKartenButtonsUnten.setBackground(new Color(0,0,0,1));
		jokerkarten.setBackground(new Color(0,0,0,1));
		chatPanel.setBackground(new Color(0,0,0,1));
		
		Tisch.add(hauptPanel);
		Tisch.setExtendedState(Frame.MAXIMIZED_BOTH);
		Tisch.setVisible(true);
		Tisch.setResizable(false);
		Tisch.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}
	
	/***************************************************************************************
	Getters und Setters
	****************************************************************************************/

	public void setHand(ArrayList<Card> hand){
		this.hand = hand;
	}
	
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	
	public void setNeuGestartet(boolean neuGestartet){
		this.neuGestartet = neuGestartet;
	}
	
	public boolean getNeuGestartet(){
		return this.neuGestartet;
	}
	
	public int getBubeAnzahl(){
		return this.bubeAnzahl;
	}
	
	public void setBubeAnzahl(int bubeAnzahl){
		this.bubeAnzahl = bubeAnzahl;
		this.lblBube.setText("Bube :" +this.bubeAnzahl);
	}
	
	public int getDameAnzahl(){
		return this.dameAnzahl;
		
	}
	
	public void setDameAnzahl(int dameAnzahl){
		this.dameAnzahl = dameAnzahl;
		this.lblDame.setText("Dame :" +this.dameAnzahl);
	}
	
	public int getKoenigAnzahl(){
		return this.koenigAnzahl;
	}
	
	public void setKoenigAnzahl(int koenigAnzahl){
		this.koenigAnzahl = koenigAnzahl;
		this.lblKoenig.setText("Koenig :" +this.koenigAnzahl);
	}
	
	public void setAnzahlKarten(int handKarten){
		this.lblHandkarten.setText("Spielkarten :" + this.handkarten);
	}
	
	public JTextArea getTxtAEingabe(){
		return this.txtAEingabe;
	}
	
	public JTextArea getTxtAChat(){
		return this.txtAChat;
	}
	
	


	/***************************************************************************************
	Methode fÃŒr die Buttons
	****************************************************************************************/
 	public void karteAuspielen(){
	int jokerWert = 1;
	String jokerFarbe = "";
		
		//Ist eine Karte angewaehlt wird sie der ArrayList gespielteKarte hinzugefuegt (JokerKarten)
		for(int i = 0;i<3;i++){
			if(jokerKarten[i].getBorder() == gedrucktBorder){
				//Fordert den Spieler auf Falls eine Jokerkarte ausgewaehlt wurde eine Karte und eine Farbe einzugeben
				jokerWert = jokerWert(i);
				
				//Nur im dreispielermodus gebraucht
				jokerFarbe = jokerFarbe();
								
				
				//Diese zeile erstellt eine Copy der Karte in die kartenKontrolle		
				gespielteKarten.add(new Card(hand.get(i).getWert(),hand.get(i).getName(),hand.get(i).getBild(),hand.get(i).getPunkte(),hand.get(i).getFarbe(),hand.get(i).getJoker(),jokerWert,jokerFarbe));
							
			}
		}
		//Ist eine Karte angewaehlt wird sie der ArrayList gespielteKarte hinzugefuegt (Hand Karten)
		for(int i = 3;i<17;i++){
			if(btnKarte[i-3].getBorder() == gedrucktBorder){
				
				//Diese zeile erstellt eine Copy der Karte in die kartenKontrolle
				gespielteKarten.add(new Card(hand.get(i).getWert(),hand.get(i).getName(),hand.get(i).getBild(),hand.get(i).getPunkte(),hand.get(i).getFarbe()));
	
			}
		}
		
		
		if(jokerWert==0 || jokerFarbe.equals("0")){
			
			JOptionPane.showMessageDialog (this, "Sie haben flasche Angaben zur Jokerkarte gemacht","Ungï¿œltige Werte",1);
			gespielteKarten.clear();
			
		}else{
			
			//Sortiert die FeldKarten nach groesse
			Collections.sort(gespielteKarten);
			
			
			//Wenn bereits Karten ausgespielt wurden, muss Stechlogik ueberprueft werden
			if(Client.game.getFeldkarten().size()>0){
					
				//Wenn eine Einzelkarte gespielt wurde und sie hoecher ist wie die bereits gespielte Karte, stich erfolgreich
				if(istEinzel(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() &&  gespielteKarten.size() == Client.game.getFeldkarten().size()){	
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
					
				}
				
				
				//Wenn die Karten eine Bombe sind können sie alles stechen ausser eine höhere Bombe
				
				//Bombe sticht eine andere Bombe wenn sie höher ist wie die andere Bombe
				else if(gespielteKarten.size() > 0 && istBombe(gespielteKarten) < istBombe(Client.game.getFeldkarten())){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Bombe sticht alle anderen Kombinationen ausser hoeher Bomben
				else if(istBombe(gespielteKarten) > 0 && !Client.game.getBombe()){
					Client.game.setBombe(true);
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten ein Paar sind und sie hoecher sind wie das bereits gespielte Paar, Stich erfolgreich
				else if(istPaar(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() &&  gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Drillinge sind und sie hoecher sind wie die bereits gespielten Drillinge, stich erfolgreich
				else if(istDrilling(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert()&&  gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Vierlinge sind und sie hoecher sind wie die bereits gespielten Vierling, Stich erfolgriech
				else if(istVierling(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert()&&  gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Fuenflinge sind und sie hoecher sind wie die bereits gespielten Fï¿œnflinge Stich erfolgreich
				else if(istFuenfling(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert()&&  gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Sechslinge sind und sie hoecher sind wie die bereits gespielten Sechslinge, Stich erfolgreich
				else if(istSechsling(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert()&&  gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}		
				
				//Wenn die Karten Sieblinge sind und sie hoecher sind wie die bereits gespielten Sieblinge, Stich erfolgreich
				else if(istSiebling(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert()&&  gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Achtlinge sind und sie hoecher sind wie die bereits gespielten Achtlinge, Stich erfolgreich
				else if(istAchtling(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert()&&  gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine dreier Strasse sind und sie hoecher sind wie die bereits gespielte dreier Strasse, stich erfolgreich
				else if(istStrasseDrei(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine vierer Strasse sind und sie hoecher sind wie die bereits gespielte vierer Strasse, stich erfolgreich
				else if(istStrasseVier(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine fuenfer Strasse sind und sie hoecher sind wie die bereits gespielte fuenfer Strasse, stich erfolgreich
				else if(istStrasseFuenf(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine sechser Strasse sind und sie hoecher sind wie die bereits gespielte sechser Strasse, stich erfolgreich
				else if(istStrasseSechs(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine siebner Strasse sind und sie hoecher sind wie die bereits gespielte siebner Strasse, stich erfolgreich
				else if(istStrasseSieben(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine achter Strasse sind und sie hoecher sind wie die bereits gespielte achter Strasse, stich erfolgriech
				else if(istStrasseAcht(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine neuner Strasse sind und sie hoecher sind wie die bereits gespielte neuner Strasse, stich erfolgreich
				else if(istStrasseNeun(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine zehner Strasse sind und sie hoecher sind wie die bereits gespielte zehner Strasse, stich erfolgriech
				else if(istStrasseZehn(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine elfer Strasse sind und sie hoecher sind wie die bereits gespielte elfer Strasse, stich erfolgriech
				else if(istStrasseElf(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine zwoelfer Strasse sind und sie hoecher sind wie die bereits gespielte zwoelfer Strasse, stich erfolgreich
				else if(istStrasseZwoelf(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Paar Strasse ist spiele und sie hoecher ist wie die bereits gespielte Paar Strasse, stich erfolgreich
				else if(istPaarStrasse(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Drilling Strasse ist und sie hoecher ist wie die bereits gespielte Drilling Strasse, stich erfolgreich
				else if(istDrillingStrasse(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Vierling Strasse ist und sie hoecher ist wie die bereits gespielte Vierling Strasse, stich erfolgreich
				else if(istVierlingStrasse(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Fuenfling Strasse ist und sie hoecher ist wie die bereits gespielte Fuenflng Strasse, Stich erfolgreich
				else if(istFuenflingStrasse(gespielteKarten) && gespielteKarten.get(0).getWert() > Client.game.getFeldkarten().get(0).getWert() && gespielteKarten.size() == Client.game.getFeldkarten().size()){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn es keine gueltige Kombination ist, wird dem Spieler eine Nachricht gesendet das der Zug ungueltig ist
				else{
					JOptionPane.showMessageDialog (this, "Bitte Ueberpruefen Sie die Karten nochmals","Ungueltige Zug",1);
					gespielteKarten.removeAll(gespielteKarten);
				}
					
			
				
			//Falls noch keine FeldKarten ausgespielt wurden muss nur die Ausspiellogik betrachtet werden	
			}else{
				//Alle Kontrollen werden durchgefuehrt ob es gueltig auszuspielende Karten sind
				//Wenn die Karte eine einzelkarte ist dann Spiel sie aus
				if(istEinzel(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
					
				}
				
				//Wenn die Karten eine Bombe sind
				else if(istBombe(gespielteKarten) > 0){
					Client.game.setBombe(true);
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				//Wenn die Karten ein Paar sind dann Spiel sie aus
				else if(istPaar(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Drillinge sind dann Spiele sie aus
				else if(istDrilling(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Vierlinge sind dann Spiele sie aus
				else if(istVierling(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Fuenflinge sind dann Spiele sie aus
				else if(istFuenfling(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Sechslinge sind dann Spiele sie aus
				else if(istSechsling(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}		
				
				//Wenn die Karten Sieblinge sind dann Spiele sie aus
				else if(istSiebling(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten Achtlinge sind dann Spiele sie aus
				else if(istAchtling(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine dreier Strasse sind spiele sie aus
				else if(istStrasseDrei(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine vierer Strasse sind spiele sie aus
				else if(istStrasseVier(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine fuenfer Strasse sind spiele sie aus
				else if(istStrasseFuenf(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine sechser Strasse sind spiele sie aus
				else if(istStrasseSechs(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine siebner Strasse sind spiele sie aus
				else if(istStrasseSieben(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine achter Strasse sind spiele sie aus
				else if(istStrasseAcht(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine neuner Strasse sind spiele sie aus
				else if(istStrasseNeun(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine zehner Strasse sind spiele sie aus
				else if(istStrasseZehn(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine elfer Strasse sind spiele sie aus
				else if(istStrasseElf(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine zwoelfer Strasse sind spiele sie aus
				else if(istStrasseZwoelf(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Paar Strasse ist spiele sie aus
				else if(istPaarStrasse(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Drilling Strasse ist spiele sie aus
				else if(istDrillingStrasse(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Vierling Strasse ist spiele sie aus
				else if(istVierlingStrasse(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn die Karten eine Fuenfling Strasse ist spiele sie aus
				else if(istFuenflingStrasse(gespielteKarten)){
					karteAnzeigen(gespielteKarten);
					kartenFeldKopieren(gespielteKarten);
					karteLoeschen();
					sendeGameObjekt();
				}
				
				//Wenn es keine gueltige Kombination ist, wird dem Spieler eine Nachricht gesendet das der Zug ungï¿œltig ist
				else{
					JOptionPane.showMessageDialog (this, "Bitte ueberpruefen Sie die Karten nochmals","Ungueltiger Zug",1);
					
					gespielteKarten.removeAll(gespielteKarten);
				}
				
				
			}
			
			
		}
		//Macht alle Border weg
		keinBorder();
	}
	
 	//Methode zum passen eines Spielzuges
	public void passen(){
		
		if(Client.game.getSpieler(0).getAmZug()){
			Client.game.getSpieler(0).setPassen(true);
		}
		else if(Client.game.getSpieler(1).getAmZug()){
			Client.game.getSpieler(1).setPassen(true);
		}
		
		sendeGameObjekt();
		
	}
	
	//Methode welche die Nachricht in das ChatFenster schreibt und anschliessend das objekt sendet
	public void schreiben(){
			
			chat = new Chat();
			chat.setMessage(this.getTxtAEingabe().getText());
			chat.setSpieler(this.name);
		}
	
	/***************************************************************************************
	Methoden welche zur Hilfe benutzt werden und oft aufgerufen werden
	****************************************************************************************/
	//Macht alle Buttons sichtbar
	public void buttonsSichtbar(){
		for(int i = 0; i<jokerKarten.length;i++){
			jokerKarten[i].setVisible(true);
		}
		for(int i = 0;i<btnKarte.length;i++){
			btnKarte[i].setVisible(true);
		} 
	}
	
	//Methode welche alle Karten in der mitte visuel loescht
	public void kartenFeldLoeschen(){
		
		for(int i = 0; i < anzeigeKarten.length; i++){
			
			anzeigeKarten[i].setIcon(null);
		}
		
	}
	
	//Methode welche alle Borders der buttons zurÃŒcksetzt
 	public void keinBorder(){
 		//Schlaufe fuer die Jokerkarten
 		for(int i = 0;i<3;i++){
			if(jokerKarten[i].getBorder() == gedrucktBorder){
				gedruckt(gedrucktJoker[i]);
				jokerKarten[i].setBorder(UIManager.getBorder("Button.border"));
				gedrucktJoker[i] = gedruckt(gedrucktJoker[i]);
			}
		}
		//Schlaufe fuer die Handkartn
		for(int i = 3;i<17;i++){
			if(btnKarte[i-3].getBorder() == gedrucktBorder){
				gedruckt(gedrucktHand[i-3]);
				btnKarte[i-3].setBorder(UIManager.getBorder("Button.border"));
				gedrucktHand[i-3] = gedruckt(gedrucktHand[i-3]);

			}
		}
 	}
 	
 	//Setzt den Wert und die Punkte der Karten auf 0 wenn ausgespielt, dient zur Kontrolle ob Handkarten leer am Ende
 	private void karteLoeschen(){
 		
 		//Schlaufe für die JokerKarten
 		for(int i = 0;i<3;i++){
 			//Ueberpruefung welche sicherstellt das nur der Wert von ausgewählten Karten auf 0 gesetzt wird
			if(jokerKarten[i].getBorder() == gedrucktBorder){
				jokerKarten[i].setVisible(false);
				//Die ausgespielte Karte wird auf null gesetzt, weil wir mit diesem Wert ÃŒberprÃŒfen ob die Handkarten leer sind
				if(Client.game.getSpieler(0).getAmZug()){
					Client.game.getSpieler(0).getHandKarten().get(i).setWert(0);
					Client.game.getSpieler(0).getHandKarten().get(i).setPunkte(0);
				}else{
					Client.game.getSpieler(1).getHandKarten().get(i).setWert(0);
					Client.game.getSpieler(1).getHandKarten().get(i).setPunkte(0);
				}
			}
		}
		//Schlaufe für die restlichen Handkarten
		for(int i = 3;i<17;i++){
			//Ueberpruefung welche sicherstellt das nur der Wert von ausgewählten Karten auf 0 gesetzt wird
			if(btnKarte[i-3].getBorder() == gedrucktBorder){
				btnKarte[i-3].setVisible(false);
				//Ueberpruefung welcher Spieler am Zug ist
				if(Client.game.getSpieler(0).getAmZug()){
					Client.game.getSpieler(0).getHandKarten().get(i).setWert(0);
					Client.game.getSpieler(0).getHandKarten().get(i).setPunkte(0);
				}else{
					Client.game.getSpieler(1).getHandKarten().get(i).setWert(0);
					Client.game.getSpieler(1).getHandKarten().get(i).setPunkte(0);
				}
			}
		}
 	
 	}
 	
	//Methode welche die gespielten Karten in die Feldkarten kopiert
	public void kartenFeldKopieren(ArrayList<Card> karten){
	
		
		//Clearet das Array welche Feldkarten speichert
		Client.game.getFeldkarten().clear();
			
		//added dem Array die neu Ausgespielten Feldkarten
		for (int k = 0; k < karten.size(); k++){
			Client.game.getFeldkarten().add(new Card(karten.get(k).getWert(),karten.get(k).getName(), karten.get(k).getBild(), 
					karten.get(k).getPunkte(), karten.get(k).getFarbe(), karten.get(k).getJoker(),karten.get(k).getJokerWert(),karten.get(k).getJokerFarbe()));
		}
		
		//Karten werden dem Array Ausgespielte Karten übergeben welche wiederum im Falle eines Runden Sieger diesem zur Punkteberechnung übergeben werden
		Client.game.addAusgespielteKarten(Client.game.getFeldkarten());
			
		gespielteKarten.clear();
	}

	//Methode welche die Karten in der Mitte anzeigt und die entsprechenden Handkarten nicht mehr sichtbar macht
	public void karteAnzeigen(ArrayList<Card> karten){
		
		//Bevor neue Karten angezeigt werden können müssen die vorhandenen Visuel gelöscht werden
		kartenFeldLoeschen();
		
		//Schlaufe welche für die anzahl ausgespielter Karten durchläuft und diese visuell in die Mitte überträgt
		for(int i =0;i<karten.size();i++){
			anzeigeKarten[i].setIcon(karten.get(i).getBild());
		}
		
	}
	
	//Methode welche die die Wetten eingiebt
	public void wetten(){
		int wette = 0;
		boolean gueltig = false;
		int eingabe = Integer.parseInt(EigeneWetten.getText());
		
		//3 Moeglichkeiten zu Wetten gar nicht (0) klein (15) gross (30)
		if(eingabe == 0){
			wette = 0;
			gueltig = true;
		}
		else if(eingabe == 15){
			wette = 15;
			gueltig = true;
		}
		else if(eingabe == 30){
			wette = 30;
			gueltig = true;
		}
		//Falls eine Falsche Wette getätigt wurde muss der Benutzer Informiert werden
		else{
			JOptionPane.showMessageDialog (null, "<html>Bitte ueberpruefen Sie die Eingabe nochmals <br>"
					+ " Moegliche Eingaben : 0 (nicht Wetten) 15 (kleine Wette) 30 (grosse Wette)","Ungueltige Eigabe",1);
		}
		
		//Ueberpruefung ob Erflogreich Gewettet wurde
		if(gueltig){
			//Falls erfolgreich gewettet wurde müssen Wetten auf den Spieler übertragen werden
			for (int i = 0; i < Client.game.getSpielerList().size(); i++){
				//Spieltisch immer gleicher name wie Spieler daher kann anhand des Namens Identifiziert werden welche Wette zu Welchem Spieler
				if(this.name.equals(Client.game.getSpielerList().get(i).getSpielerName())){
					setWette(true);
					Client.game.getSpielerList().get(i).setWette(wette);
					Client.game.getSpielerList().get(i).setGewettet(true);
					
				}
			}
		}
	}
		
	//Methode welche den Spieler auffordert einen Wert fuer den Joker einzugeben und diese zurï¿œck gibt
	public int jokerWert(int i){
		int karteWert = 15;
		
		while(true){
			
			boolean kartenStatus = true;
			try{
				String str = JOptionPane.showInputDialog (null, "Wert fuer die Jokerkarte:");
				karteWert = Integer.parseInt(str);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog (this, "Bitte Gueltigen Wert Eingeben!","Ungueltiger Wert",1);
				kartenStatus = false;
			}
			if(kartenStatus){
				break;
			}
		}
		
		
		//ï¿œberprï¿œft um welche Jokerkarte es sich handelt und gibt falls noetig die anweisung einen Richtigen Wert enzugeben = return 0
		if(i==0){
			if(karteWert > 11 || karteWert <2){
				JOptionPane.showMessageDialog (this, "Ein Bube kann nur den Wert zwischen 2 - 11 annehmen","Ungï¿œltiger Wert",1);
				return 0;
			}
		}
		else if(i==1){
			if(karteWert > 12 || karteWert <2){
				JOptionPane.showMessageDialog (this, "Eine Dame kann nur den Wert zwischen 2 - 12 annehmen","Ungï¿œltiger Wert",1);
				return 0;
			}
		}
		else if(i==2){
			if(karteWert > 13 && karteWert <2){
				JOptionPane.showMessageDialog (this, "Ein Koenig kann nur den Wert zwischen 2 - 13 annehmen","Ungï¿œltiger Wert",1);
				return 0;
			}
		}
		return karteWert;
	}
	
	//Methode welche den Spieler auffordert eine Farbe fuer den Joker einzugeben und diese zurueck gibt
	public String jokerFarbe(){
		String farbe = null;
		
		while(true){
			farbe = JOptionPane.showInputDialog (null, "Wert fuer die Jokerfarbe:");
			
			//ueberprueft ob die eingegeben farbe keine von den zulaessigen ist und teilt die moeglichkeiten dem Benutzer mit
			if(farbe.equals("rot") || farbe.equals("gruen") || farbe.equals("gelb") || farbe.equals("grau") || farbe.equals("orange")){
				break;
			}
			JOptionPane.showMessageDialog (this, "Gueltige Farben sind: rot, gelb, grau, gruen, orange","Ungueltige Farbe",1);
		}
		
		return farbe;
	}
		
	
	//Methode welche den Wert und die Farbe einer Jokerkarte wechselt
	public void jokerWertWechsel(ArrayList<Card> karten){
		for(int i =0; i<karten.size();i++){
			if(karten.get(i).getJoker()){
				karten.get(i).setWert(karten.get(i).getJokerWert());
				karten.get(i).setFarbe(karten.get(i).getJokerFarbe());
			}
		}
	}
	
	//Funktion welche den Boolean wert wechslet und so sieht ob die Karte gedruckt ist oder nicht 
	public boolean gedruckt(boolean gedruckt){
		if(gedruckt){
			gedruckt = false;
		}
		else if(!gedruckt){
			gedruckt = true;
		}
		return gedruckt;
	}

	//Funktion welche die Bilder in die einzelnen Buttons ladet
	public void ladetBilder(ArrayList<Card> hand){
		
		
		
		
		//Diese for Schleife dient dazu dem Spieler die JokerKarten zu verteilen
		
		//Die Zaehler Variable stellt die Anzahl Karten dar die in diesem Moment noch zu verteilen sind
		for(int i = 0; i<3; i++){
			
			if(hand.get(i).getName().equals("bube")){
				
				jokerKarten[0].setIcon(hand.get(i).getBild());
				

			}
			
			else if(hand.get(i).getName().equals("dame")){
				
				jokerKarten[1].setIcon(hand.get(i).getBild());
				

			}
			
			else if(hand.get(i).getName().equals("koenig")){
				
				jokerKarten[2].setIcon(hand.get(i).getBild());
				

			}
			
		}
		
		//Diese for Schleife dient dazu die restlichen Handkarten zu verteilen
		for(int i = 3; i< 17; i++){
			
			if(hand.get(i).getWert()==2){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
							

			}
			
			else if(hand.get(i).getWert()==3){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			
			else if(hand.get(i).getWert()==4){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==5){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
			
			}
			else if(hand.get(i).getWert()==6){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==7){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==8){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==9){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==10){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==11){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==12){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			else if(hand.get(i).getWert()==13){
				
				btnKarte[i-3].setIcon(hand.get(i).getBild());
				
			}
			
		}
		
		
	}
	
	//Methode welche anhand der Reihenfolge der Farben des Sets die anderen Sets farblich sortiert
	public boolean karteFarbeSortieren(int set, ArrayList<Card> karten){
		
		int ueberpruefen = 0;
		int kartePosition = 0;
		String[] farben = new String[set]; //Dieser Array dient zur Vergleichsbasis der Farben
		Card[] temp = new Card[set]; // Hier werden die naechsten Karten des sets zwischengespeichert
		int karteErsetzen =set;

		//Diese Schleife nimmt jede Karte des Sets und fuegt sie dem farben Array hinzu
		for(int i =0;i<set;i++){

			farben[i] = karten.get(i).getFarbe();
						
		}
		
		//Diese Schleife wird fuer jedes weitere Set durchgefuehrt (Drilling -1 = 2)
		for(int i = 0;i<karten.size()/set-1; i++){
			
			//Speichert das naechst groessere Set der Strasse in den temp Array
			for(int k = 0; k<set; k++){
				temp[k] = karten.get(kartePosition+set);
				kartePosition++;
			}
			
			//Die erste Karte in einem set wird mit jeder Karte des folgenden Sets verglichen dann die nï¿œchsten		
			//Fï¿œr jede anzahl Karte in einem Set wird diese Schleife durchgefuehrt, weil alle verglichen werden mï¿œssen
			for(int j = 0; j<set; j++){
				
				//Diese Schleife ueberprueft die Farben und setzt die richtige Karte an die richtige Reihenfolge
				for(int k = 0;k<set;k++){
					if(farben[j].equals(temp[k].getFarbe())){
						karten.set(karteErsetzen, temp[k]);
						karteErsetzen++;
						ueberpruefen++;
					}
				}
			}
			
		}
		if(ueberpruefen == karten.size()-
				set){
			return true;
		}else{
			return false;
		}
		
	}
	
	//Methode welche ueberprueft ob es das gleiche Set ist (Paar, Drilling etc)
	public boolean istSet(ArrayList<Card> karten, int set){
		

		int bedingung = 0;
		
		int kartePosition = 0;	
		
		//Wenn eine Strasse aus Sets >1 zb Paar, Drilling etc besteht braucht es folgenge Logik
		if(set>1){
		
			//Es muss fuer jedes Set in der Strasse einmal das Set ueberprueft werden
			for(int i = 0;i<karten.size()/set;i++){
				
				//Fuer jedes einzelne Set muss die Anzahl Karten des set minus 1 ueberprueft werden bsp. Paar erste mit zweite Karte = 1 ï¿œberprï¿œfung
				for(int j = 0; j<set-1;j++){
					
					//Vergleicht zwei Karten innerhalb eines Sets
					if(karten.get(kartePosition).getWert() == karten.get(kartePosition+1).getWert()){
						bedingung++;
					}
				}
				kartePosition+=set;
			}
			
			//Die Bedingung wird durch eine Mathematische reihe getroffen
			if(bedingung==karten.size()/set*(set-1)){
				return true;
			}else{
				return false;
			}
		
		//Wenn kein Set vorhanden ist Handelt es sich um eine simple Strasse, folgende Logik wird angewant
		}else{

			for(int i = 0;i<karten.size()-1;i++){
				
				if(karten.get(i).getWert() == karten.get(i+1).getWert()){
					bedingung++;
				}
				
			}

			if(bedingung==karten.size()-1){				
				return true;
			}else{
				return false;
			}
		}
	}
	
	//Methode welche ueberprueft ob es den Charakter einer Strasse aufweist
	public boolean istStrasse(ArrayList<Card> karten, int set){
		
		int bedingungWert = 0;
		int bedingungFarbe = 0;
		int kartePosition = 0;
		
		//Wenn der Wert der drei Karten immer um eins hoeher ist
		for(int i = 0; i < karten.size()/set-1; i++){
			if(karten.get(kartePosition).getWert() == karten.get(kartePosition+set).getWert()-1){
				bedingungWert++;
				kartePosition+=set;
			}
		}
		if(set==1){
			for(int i = 0;i< karten.size()-1;i++){
				if(karten.get(i).getFarbe().equals(karten.get(i+1).getFarbe())){
					bedingungFarbe++;
				}
			}
		}
		if(bedingungWert==karten.size()/set-1){
			if(set>1){
				return true;
			}else{
				if((bedingungFarbe == karten.size()-1)){
					return true;
				}
			}
				
		}
		
		return false;
	}
	
	//Methode welche die Spiel Buttons disabled oder Enabled je nach dem wer am Zug ist
	public void amZugButtons(boolean amZug){

		this.jbtPassen.setEnabled(amZug);
		this.jbtSpielen.setEnabled(amZug);
		
	}
		
	//Methode welche die Informationen des Gegners in den Spieltisch ladet
	public void setGegnerInfos(int hatBube, int hatDame, int hatKoenig){
		
		this.setBubeAnzahl(hatBube);
		this.setDameAnzahl(hatDame);
		this.setKoenigAnzahl(hatKoenig);
		
	}
		
	//Methode zum senden des Spielobjektes
	public void sendeGameObjekt(){
		
		try{
			Spieltisch.out.writeObject(Client.game);
		}catch (IOException e) {
			e.printStackTrace();
		}
			
	}
			

	//Methode zum senden des Chatobjektes
	public void sendeChatObjekt(){
		try{

			Spieltisch.out.writeObject(chat);
			out.flush();
			}catch (IOException e) {
				e.printStackTrace();
			}
	}

	
	/***************************************************************************************
	Methoden welche kontrolliert ob die bei einem neuen Hand auszuspielenden Karten legitim 
	sind oder nicht, gibt einen true wert zurueck	
	****************************************************************************************/
	//Methode zum ueberpruefen ob es eine einzelKarte ist
	public boolean istEinzel(ArrayList<Card> karten){
		
		//Ist nur eine Karte in den zu ueberpruefenden Karte ist es immer eine einzelne Karte
		if(karten.size()==1){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	//Methode zum ueberpruefen ob es ein Paar ist
	public boolean istPaar(ArrayList<Card> karten){
		
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Ein Paar ist nur moeglich wenn es zwei Karten sind
		if(karten.size()==2){
			//Wenn der Wert beider Karten gleich ist dann ist es ein Paar
			if(istSet(karten,1)){
				return true;
			}else{
				return false;
			}
			
			
		}

		return false;
	}

	//Methode zum ueberpruefen ob es ein Drilling ist
	public boolean istDrilling(ArrayList<Card> karten){
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		
		//Ein Drilling ist nur moeglich wenn es drei Karten sind
		if(karten.size()==3){
			//Wenn der Wert aller drei Karten gleich ist, ist es ein Drilling
			if(istSet(karten,1)){
				return true;
			}else{
				return false;
			}		
		}			
		return false;
	}

	//Methode zum ueberpruefen ob es ein Vierling ist
	public boolean istVierling(ArrayList<Card> karten){
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Ein Vierling ist nur moeglich wenn es 4 Karten sind
		if(karten.size()==4){
			
			//Wenn alle 4 Karten gleich sind ist es ein Vierling
			if(istSet(karten,1)){
				return true;
			}else{
				return false;
			}
			
		}
	
		return false;
	}

	//Methode zum ueberpruefen ob es ein Fuenfling ist
	public boolean istFuenfling(ArrayList<Card> karten){
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Wenn es 5 Karten sind dann ist es ein Fuenfling
		if(karten.size()==5){
			
			//Wenn alle 5 Karten gleich sind dann ist es ein Fuenfling
			if(istSet(karten,1)){
				return true;
			}else{
				return false;
			}
		}
	
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es ein Sechsling ist
	public boolean istSechsling(ArrayList<Card> karten){
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Wenn es 6 Karten sind dann ist es ein Sechsling
		if(karten.size()==6){
			
			//ueberpruefung ob alle Karten des Sets den gleichen Wert haben
			if(istSet(karten,1)){
				return true;
			}else {
				return false;
			}
			

		}	
		return false;
	}
	
	//Methode zum ueberpruefen ob es ein Siebling ist
	public boolean istSiebling(ArrayList<Card> karten){
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Wenn es 7 Karten sind dann ist es ein Siebling
		if(karten.size()==7){
			
			//ueberpruefung ob alle Karten des Sets den gleichen Wert haben
			if(istSet(karten,1)){
				return true;
			}else{
				return false;
			}
			
			
		}
			
		return false;
	}
	
	//Methode zum ueberpruefen ob es ein Achtling ist
	public boolean istAchtling(ArrayList<Card> karten){
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Wenn es 8 Karten sind dann ist es ein Achtling
		if(karten.size()==8){
			
			//ueberpruefung ob alle Karten des Sets den gleichen Wert haben
			if(istSet(karten,1)){
				return true;
			}else{
				return false;	
			}		
		}
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine dreier Strasse ist
	public boolean istStrasseDrei(ArrayList<Card> karten){
		
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus drei Karten hat drei Karten =)
		if(karten.size()==3){
			
			if(istStrasse(karten, 1)){
				return true;
			}
						
		}
		
		return false;
	}

	//Methode zum ueberpruefen ob es eine vierer Strasse ist
	public boolean istStrasseVier(ArrayList<Card> karten){
		
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus vier Karten hat vier Karten =)
		if(karten.size()==4){
			if(istStrasse(karten, 1)){
				return true;
			}
			
		}
		
		return false;
		
	}
	
	//Methode zum ueberpruefen ob es einee fuenfer Strasse ist
	public boolean istStrasseFuenf(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus fuenf Karten hat fuenf Karten =)
		if(karten.size()==5){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine sechser Strasse ist
	public boolean istStrasseSechs(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus sechs Karten hat sechs Karten =)
		if(karten.size()==6){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine siebner Strasse ist
	public boolean istStrasseSieben(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus sieben Karten hat sieben Karten =)
		if(karten.size()==7){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine achter Strasse ist
	public boolean istStrasseAcht(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus acht Karten hat acht Karten =)
		if(karten.size()==8){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine neuner Strasse ist
	public boolean istStrasseNeun(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus neun Karten hat neun Karten =)
		if(karten.size()==9){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine zehner Strasse ist
	public boolean istStrasseZehn(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus zehn Karten hat zehn Karten =)
		if(karten.size()==10){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine elfer Strasse ist
	public boolean istStrasseElf(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus elf Karten hat elf Karten =)
		if(karten.size()==11){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}

	//Methode zum ueberpruefen ob es eine zwoelfer Strasse ist
	public boolean istStrasseZwoelf(ArrayList<Card> karten){
		//Sortiert die Karten neu sodass die Jokerkarten an den platz kommt wie ihr Wert eingegeben wurde
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		//Eine Strasse aus zwoelf Karten hat zwoelf Karten =)
		if(karten.size()==12){
			
			if(istStrasse(karten, 1)){
				return true;
			}
		}
		
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine Strasse mit Paaren ist
	public boolean istPaarStrasse(ArrayList<Card> karten){
		
		int kartePosition = 0;
		int ueberpruefen = 0; //Checkt ob es sich um Paare handelt
		boolean ueberpruefen4 = false; //Checkt ob die PaarStrasse farblich korrekt ist

		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		
		if(karten.size()>3){
			
			//Da es nur Paar sind sollte die ausgewaehlten Karten durch 2 teilbar sein
			if (karten.size()%2 == 0) { 
				

				
				//Wenn die letzte ueberpruefung stimmt geht es weiter
				if(istSet(karten,2)){
					System.out.println("istSet funktioniert");							
					//Wenn die letzte ueberpruefung stimmt geht es weiter
					if(istStrasse(karten,2)){
						
						System.out.println("istStrasse funktioniert");	
						//ueberpruefung damit Jokerkarten nicht eine bereits existierende karte kopieren kï¿œnnen
						for(int i = 0;i < karten.size()/2; i++){
							if(karten.get(kartePosition).getFarbe() != karten.get(kartePosition+1).getFarbe()){
								ueberpruefen++;
								kartePosition+=2;
							}				
						}
					
						if(ueberpruefen== karten.size()/2){
							ueberpruefen4 = karteFarbeSortieren(2, karten);
							
							//Wenn die letzte ueberpruefung stimmt ist es eine zulaessige Paarstrasse
							if(ueberpruefen4){
								return true;
							}
							
						}					
					}
				}
			}			
		}
		return false;
	}
	
	//Methode zum ueberpruefen ob es eine Drilling Strasse ist
	public boolean istDrillingStrasse(ArrayList<Card> karten){
		
		int kartePosition = 0;
		int ueberpruefen = 0; //Checkt ob es sich um Paare handelt
		boolean ueberpruefen4 = false; //Checkt ob die PaarStrasse farblich korrekt ist

		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		
		if(karten.size()>5){
			
			//Da es nur Paar sind sollte die ausgewaehlten Karten durch 2 teilbar sein
			if (karten.size()%3 == 0) { 
				

				
				//Wenn die letzte ueberpruefung stimmt geht es weiter
				if(istSet(karten,3)){
					System.out.println("istSet funktioniert");							
					//Wenn die letzte ueberpruefung stimmt geht es weiter
					if(istStrasse(karten,3)){
						
						System.out.println("istStrasse funktioniert");	
						//ueberpruefung damit Jokerkarten nicht eine bereits existierende karte kopieren kï¿œnnen
						for(int i = 0;i < karten.size()/3; i++){
							if(karten.get(kartePosition).getFarbe() != karten.get(kartePosition+1).getFarbe()){
								ueberpruefen++;
								kartePosition+=3;
							}				
						}
					
						if(ueberpruefen== karten.size()/3){
							ueberpruefen4 = karteFarbeSortieren(3, karten);
							
							//Wenn die letzte ueberpruefung stimmt ist es eine zuluessige Paarstrasse
							if(ueberpruefen4){
								System.out.println("zulässige Paarstrasse");
								return true;
							}
							
						}					
					}
				}
			}			
		}
		return false;
	}

	//Methode zum ueberpruefen ob es eine VierlingStrasse ist
	public boolean istVierlingStrasse(ArrayList<Card> karten){
		
		int kartePosition = 0;
		int ueberpruefen = 0; //Checkt ob es sich um Paare handelt
		boolean ueberpruefen4 = false; //Checkt ob die PaarStrasse farblich korrekt ist

		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		
		if(karten.size()>7){
			
			//Da es nur Paar sind sollte die ausgewaehlten Karten durch 2 teilbar sein
			if (karten.size()%4 == 0) { 
				

				
				//Wenn die letzte ueberpruefung stimmt geht es weiter
				if(istSet(karten,4)){
					System.out.println("istSet funktioniert");							
					//Wenn die letzte ueberpruefung stimmt geht es weiter
					if(istStrasse(karten,4)){
						
						System.out.println("istStrasse funktioniert");	
						//ueberpruefung damit Jokerkarten nicht eine bereits existierende karte kopieren kï¿œnnen
						for(int i = 0;i < karten.size()/4; i++){
							if(karten.get(kartePosition).getFarbe() != karten.get(kartePosition+1).getFarbe()){
								ueberpruefen++;
								kartePosition+=4;
							}				
						}
					
						if(ueberpruefen== karten.size()/4){
							ueberpruefen4 = karteFarbeSortieren(4, karten);
							
							//Wenn die letzte ueberpruefung stimmt ist es eine zulaessige Paarstrasse
							if(ueberpruefen4){
								return true;
							}
							
						}					
					}
				}
			}			
		}
		return false;
	}

	//Methode zum ueberpruefen ob es eine Fuenfling Strasse ist
	public boolean istFuenflingStrasse(ArrayList<Card> karten){
		
		int kartePosition = 0;
		int ueberpruefen = 0; //Checkt ob es sich um Paare handelt
		boolean ueberpruefen4 = false; //Checkt ob die PaarStrasse farblich korrekt ist

		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		
		if(karten.size()>9){
			
			//Da es nur Paar sind sollte die ausgewaehlten Karten durch 2 teilbar sein
			if (karten.size()%5 == 0) { 
				

				
				//Wenn die letzte ueberpruefung stimmt geht es weiter
				if(istSet(karten,5)){
					System.out.println("istSet funktioniert");							
					//Wenn die letzte ueberpruefung stimmt geht es weiter
					if(istStrasse(karten,5)){
						
						System.out.println("istStrasse funktioniert");	
						//ueberpruefung damit Jokerkarten nicht eine bereits existierende karte kopieren kï¿œnnen
						for(int i = 0;i < karten.size()/5; i++){
							if(karten.get(kartePosition).getFarbe() != karten.get(kartePosition+1).getFarbe()){
								ueberpruefen++;
								kartePosition+=5;
							}				
						}
					
						if(ueberpruefen== karten.size()/5){
							ueberpruefen4 = karteFarbeSortieren(5, karten);
							
							//Wenn die letzte ueberpruefung stimmt ist es eine zulaessige Paarstrasse
							if(ueberpruefen4){
								return true;
							}
							
						}					
					}
				}
			}			
		}
		return false;
	
	} 
	
	//Ueberprueft ob es eine Bombe und welche im Ranking
	public int istBombe(ArrayList<Card> karten){
		
		
		/*
		 * Bomben Ranking:
		 * 1. 3 - 5 - 7 - 9 gleiche Farbe
		 * 2. B - D - K 
		 * 3. D - K
		 * 4. B - K
		 * 5. B - D
		 * 6. 3 - 5 - 7 - 9 ungleiche Farbe
		 */
		
		int testBombe6 = 0;
		int testBombe5 = 0;
		int testBombe4 = 0;
		int testBombe3 = 0;
		int testBombe2 = 0;
		int testBombe1 = 0;
		
		//Methode welche die Jokerwert in den Wert der Jokerkarte schreibt
		jokerWertWechsel(karten);

		
		//Karten neu sotieren nach ihrem Wert sodass die Jokerkarte an ihrem richtigen Platz ist
		Collections.sort(karten);
		
		
		//Bombe 6 3 - 5 - 7 - 9 ungleiche Farbe
		
		if(karten.size() == 4){

			//Bei drei beginnt die Bombe
			int zahl = 3;
			for(int i = 0; i < karten.size(); i++){
				
				//Wenn die Karte die gesuchte Zahl hat, kein Joker ist 
				if(karten.get(i).getWert()== zahl && karten.get(i).getJoker()== false){
					testBombe6++;
				}
				zahl= zahl + 2;

			}
		}
		
		//Bombe 5 B - D
		
		//Es muessen 2 Karten sein
		if(karten.size() == 2){
			
			if(Client.game.getFeldkarten().size()>0){
				for(int i = 0; i < Client.game.getFeldkarten().size(); i++){
				}
			}
			for(int i = 0; i <karten.size(); i++){
				if(karten.get(i).getWert() == 11 || karten.get(i).getWert() == 12){
					testBombe5++;
				}
			}
		}
		
		//Bombe 4 B - K
		
		//Es muessen 2 Karten sein
		if(karten.size() == 2){
			for(int i = 0; i <karten.size(); i++){
				if(karten.get(i).getWert() == 11 || karten.get(i).getWert() == 13){
					testBombe4++;
				}
			}
		}
		
		//Bombe 3 D - K
		
		//Es muessen 2 Karten sein
		if(karten.size() == 2){
			for(int i = 0; i<karten.size(); i++){
				if(karten.get(i).getWert() == 12 || karten.get(i).getWert() == 13){
					testBombe3++;
				}
			}
		}
		
		//Bombe 2 B - D - K
		
		//Es muessen 3 Karten sein
		if(karten.size() == 3){
			for(int i = 0; i<karten.size(); i++){
				if(karten.get(i).getWert() == 11 || karten.get(i).getWert() == 12 || karten.get(i).getWert() == 13){
					testBombe2++;
				}
			}
		}
		
		//Bombe 1 3 - 5 - 7 - 9
		
		//Es muessen 4 Karten sein
		if(karten.size() == 4){
			//Speichert die zu untersuchende Farbe ab
			String farbe = karten.get(1).getFarbe();
			//Bei drei beginnt die Bombe
			int zahl = 3;
			for(int i = 0; i < karten.size(); i++){
				
				//Wenn die Karte die gesuchte Zahl hat, kein Joker ist und die gleiche Farbe wie alle hat
				if(karten.get(i).getWert()== zahl && karten.get(i).getJoker()== false && karten.get(i).getFarbe() == farbe){
					testBombe1++;
				}
				zahl+=2;

			}
		}
		
		//Je nach Bombe wird gemaess der Rangliste eine anderen Wert zurueck gegeben
		if(testBombe1 == 4){
			return 1;
		}
		else if(testBombe2 == 3){
			return 2;
		}
		else if(testBombe3 == 2){
			return 3;
		}
		else if(testBombe4 == 2){
			return 4;
		}
		else if(testBombe5 == 2){
			return 5;
		}
		else if(testBombe6 == 4){
			return 6;
		}
		else{
			return 0;
		}
	}
	
	public void setWette(boolean wette){
		this.wette = wette;
	}
	
	public boolean getWette(){
		return this.wette;
	}
	
	
	
	/***************************************************************************************
	Methoden welche kontrolliert ob die bei einem neuen Hand auszuspielenden Karten legitim 
	sind oder nicht, gibt einen true wert zurueck	
	****************************************************************************************/
	
	
	
	/***************************************************************************************
	Handler welcher fuer das Anwaehlen von Karten zustaendig ist
	****************************************************************************************/
	public class clickHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			
			//Handlet die gedruckten Karten und setzt ihnen einen Border oder nicht (JokerKarten)
			for(int i = 0;i<3;i++){
				if(e.getSource() == jokerKarten[i]){
					gedrucktJoker[i] = gedruckt(gedrucktJoker[i]);
					if(gedrucktJoker[i]){
						jokerKarten[i].setBorder(gedrucktBorder);
						
					}
					else if(!gedrucktJoker[i]){
						jokerKarten[i].setBorder(UIManager.getBorder("Button.border"));
						
					}
				}
			}
			
			//Handlet die gedruckten Karten und setzt ihnen einen Border oder nicht (Handkarten)
			for(int i = 0;i<14;i++){	
				if(e.getSource() == btnKarte[i]){
					
					gedrucktHand[i] = gedruckt(gedrucktHand[i]);
					if(gedrucktHand[i]){
						btnKarte[i].setBorder(gedrucktBorder);
						
					}
					else if(!gedrucktHand[i]){
						btnKarte[i].setBorder(UIManager.getBorder("Button.border"));
						
					}
				}
			}
			

		
		}
	}
	
	/***************************************************************************************
	Handler welcher fuer das klicken der Buttons zustaendig ist
	****************************************************************************************/
	
	public class buttonHandler implements ActionListener{

	

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean aStatus = true;
			// TODO Auto-generated method stub
			if(e.getSource()== jbtSpielen){
				karteAuspielen();
			}
			else if(e.getSource()==jbtPassen){
				if(Client.game.getFeldkarten().size()>0){
					passen();
				}else{
					JOptionPane.showMessageDialog (null, "Sie sind am Zug und haben keine Karte ausgespielt!"
							,"Passen nicht möglich",2);
				}
			}
			else if(e.getSource()==jbtEingabe){
				System.out.println("möchte Chat senden");
				schreiben();
				sendeChatObjekt();
				txtAEingabe.setText("");
				System.out.println("chat gesendent mit inhalt:");
				System.out.println(chat.getMessage());
			}
			else if(e.getSource()==jbtWetten){
				
				try{
					wetten();
				}catch (NumberFormatException IOException){
					JOptionPane.showMessageDialog(rootPane, "Bitte gueltige Wette eingeben.");
					aStatus = false;
				}
				if(aStatus){
					sendeGameObjekt();
				}
			}

		}
	}
}
		
	

	
	
	




	
	
	



