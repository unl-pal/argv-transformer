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
	
	
	/***************************************************************************************
	Methode für die Buttons
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
			
			JOptionPane.showMessageDialog (this, "Sie haben flasche Angaben zur Jokerkarte gemacht","Ung�ltige Werte",1);
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
				
				
				//Wenn die Karten eine Bombe sind k�nnen sie alles stechen ausser eine h�here Bombe
				
				//Bombe sticht eine andere Bombe wenn sie h�her ist wie die andere Bombe
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
				
				//Wenn die Karten Fuenflinge sind und sie hoecher sind wie die bereits gespielten F�nflinge Stich erfolgreich
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
				
				//Wenn es keine gueltige Kombination ist, wird dem Spieler eine Nachricht gesendet das der Zug ung�ltig ist
				else{
					JOptionPane.showMessageDialog (this, "Bitte ueberpruefen Sie die Karten nochmals","Ungueltiger Zug",1);
					
					gespielteKarten.removeAll(gespielteKarten);
				}
				
				
			}
			
			
		}
		//Macht alle Border weg
		keinBorder();
	}
	
 	//Methode welche alle Borders der buttons zurücksetzt
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
 		
 		//Schlaufe f�r die JokerKarten
 		for(int i = 0;i<3;i++){
 			//Ueberpruefung welche sicherstellt das nur der Wert von ausgew�hlten Karten auf 0 gesetzt wird
			if(jokerKarten[i].getBorder() == gedrucktBorder){
				jokerKarten[i].setVisible(false);
				//Die ausgespielte Karte wird auf null gesetzt, weil wir mit diesem Wert überprüfen ob die Handkarten leer sind
				if(Client.game.getSpieler(0).getAmZug()){
					Client.game.getSpieler(0).getHandKarten().get(i).setWert(0);
					Client.game.getSpieler(0).getHandKarten().get(i).setPunkte(0);
				}else{
					Client.game.getSpieler(1).getHandKarten().get(i).setWert(0);
					Client.game.getSpieler(1).getHandKarten().get(i).setPunkte(0);
				}
			}
		}
		//Schlaufe f�r die restlichen Handkarten
		for(int i = 3;i<17;i++){
			//Ueberpruefung welche sicherstellt das nur der Wert von ausgew�hlten Karten auf 0 gesetzt wird
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
 	
	/***************************************************************************************
	Handler welcher fuer das Anwaehlen von Karten zustaendig ist
	****************************************************************************************/
	public class clickHandler implements ActionListener{
	}
	
	/***************************************************************************************
	Handler welcher fuer das klicken der Buttons zustaendig ist
	****************************************************************************************/
	
	public class buttonHandler implements ActionListener{
	}
}
		
	

	
	
	



