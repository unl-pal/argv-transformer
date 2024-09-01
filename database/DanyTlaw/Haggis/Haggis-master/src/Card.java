import java.io.Serializable;

import javax.swing.ImageIcon;

//Diese Klasse enth�lt die Logik einer Karte
public class Card implements Comparable<Card>, Serializable {
	
	//Die Nummer welche auf der Karte darauf ist
	int wert;
	//Der name ist farbe +nummer
	String name;
	//Jede Karte hat ein eigenes Bild
	ImageIcon bild;
	//Jede Karte hat verschiedene Punkte die man gewinnen kann
	int punkte;
	//Jede Karte hat ihre eigene Farbe
	String farbe;
	//Eine Karte kann auch ein Joker sein
	boolean joker;
	//Der Joker wert ist der anzunehmende Wert der Karte
	int jokerWert;
	//Die Jokerfarbe ist die anzunehmende Jokerfarbe
	String jokerFarbe;
	



	

	
}
