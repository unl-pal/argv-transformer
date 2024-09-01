import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Diese Klasse enthaelt die Logik eines Deckes
public class Deck {
	
	
	//Bilder fuer die Karten
	//Variablen fuer Graue Karten
	public Card grau2;
	public Card grau3;
	public Card grau4;
	public Card grau5;
	public Card grau6;
	public Card grau7;
	public Card grau8;
	public Card grau9;
	public Card grau10;
	
	//Variablen fuer Gelbe Karten
	public Card gelb2;
	public Card gelb3;
	public Card gelb4;
	public Card gelb5;
	public Card gelb6;
	public Card gelb7;
	public Card gelb8;
	public Card gelb9;
	public Card gelb10;
	
	//Variablen fuer orange Karten
	public Card orange2;
	public Card orange3;
	public Card orange4;
	public Card orange5;
	public Card orange6;
	public Card orange7;
	public Card orange8;
	public Card orange9;
	public Card orange10;	
	
	//Variablen fuer rote Karten
	public Card rot2;
	public Card rot3;
	public Card rot4;
	public Card rot5;
	public Card rot6;
	public Card rot7;
	public Card rot8;
	public Card rot9;
	public Card rot10;
	
	//Variablen fuer gruene Karten
	public Card gruen2;
	public Card gruen3;
	public Card gruen4;
	public Card gruen5;
	public Card gruen6;
	public Card gruen7;
	public Card gruen8;
	public Card gruen9;
	public Card gruen10;
	
	//JokerKarten	
	public Card bube1;	
	public Card dame1;
	public Card koenig1;
	
	private Card bube2;
	private Card dame2;
	private Card koenig2;
	
	private Card bube3;
	private Card dame3;
	private Card koenig3;

	public boolean spieler1 = true;
	
	public ArrayList<Card> deck = new ArrayList<Card>();
	
	public ArrayList<Card> haeggis = new ArrayList<Card>();
	
	public ArrayList<ArrayList<Card>> haende = new ArrayList<ArrayList<Card>>(3);
	public ArrayList<Card> handKarten1 = new ArrayList<Card>();
	public ArrayList<Card> handKarten2 = new ArrayList<Card>();
	
	private int kartenBreite = 60;
	private int kartenHoehe = 100;
	
	
	//Methode welche die Karten nach den Regeln auf zwei Spieler aufteilt
	public void aufteilen(int anzahl){
		
		
		
		
		
		//Zwei Spieler aufteilung
		if(anzahl==2){
			
			int j = 0;
			while(j < deck.size()){
				
				if(deck.get(j).getFarbe().equals("grau")){
					if(deck.get(j).getFarbe() != null){
					deck.remove(j);
					}
				}
				else{
					j++;
				}
			}
			
			//Schleife welche einen Buben entfernt
			for(int i =0;i<deck.size();i++){
				if(deck.get(i).getName().equals("bube")){
					deck.remove(i);
					break;
				}
			}
			//schleife welche eine Dame entfernt
			for(int i =0;i<deck.size();i++){
				if(deck.get(i).getName().equals("dame")){
					deck.remove(i);
					break;
				}
			}
			//Schleife welche einen Koenig entfernt
			for(int i =0;i<deck.size();i++){
				if(deck.get(i).getName().equals("koenig")){
					deck.remove(i);
					break;
				}
			}
			


			//While Schleife welche beiden Spielern einen Buben gibt
			int k = 0;
			while(true){
				if(spieler1){
					if(deck.get(k).getName().equals("bube")){
						handKarten1.add(deck.get(k));
						deck.remove(k);
						spieler1 = false;
						k--;
					}
				}
				else if(!spieler1){
					if(deck.get(k).getName().equals("bube")){
						handKarten2.add(deck.get(k));
						deck.remove(k);
						spieler1 = true;
						break;
					}
				}
				k++;
			}

			//While Schleife welche beiden Spielern eine Dame gibt
			k = 0;
			while(true){
				if(spieler1){
					if(deck.get(k).getName().equals("dame")){
						handKarten1.add(deck.get(k));
						deck.remove(k);
						spieler1 = false;
						k--;
					}
				}
				else if(!spieler1){
					if(deck.get(k).getName().equals("dame")){
						handKarten2.add(deck.get(k));
						deck.remove(k);
						spieler1 = true;
						break;
					}
				}
				k++;
			}
			
			//While Schleife welche beiden Spielern einen Koenig gibt
			k = 0;
			while(true){
				if(spieler1){
					if(deck.get(k).getName().equals("koenig")){
						handKarten1.add(deck.get(k));
						deck.remove(k);
						spieler1 = false;
						k--;
					}
				}
				else if(!spieler1){
					if(deck.get(k).getName().equals("koenig")){
						handKarten2.add(deck.get(k));
						deck.remove(k);
						spieler1 = true;
						break;
					}
				}
				k++;
			}
			
			
			//Schleife in der 14 mal eine Zufallszahl gemacht wird zwischen

			
			for(int i = 0; i<28;i++){
				//Variable welche dafuer sorgt das die Karten abwechselnd verteilt werden
				
				
				int decZufallZahl = (int) (Math.random()*deck.size()+1);
				int zufallsZahl = Math.round(decZufallZahl) - 1;
				System.out.println(zufallsZahl);
				if(spieler1){
					handKarten1.add(deck.get(zufallsZahl));
					deck.remove(zufallsZahl);
					spieler1 = false;
				}
				else if(!spieler1){
					handKarten2.add(deck.get(zufallsZahl));
					deck.remove(zufallsZahl);
					spieler1=true;
				}
				
			}
			System.out.println("-----------------------------HAGGIS-------------------------------");
			for(Card hKarte : deck){
				haeggis.add(hKarte);
				System.out.println(hKarte.getPunkte());
			}
			deck.removeAll(deck);
			
			haende.add(handKarten1);
			haende.add(handKarten2);
			
			
			System.out.println("------------------------------JokerKartenWert handkarten 1------------------------------");
			for (int i = 0; i < 3; i++){
				System.out.println(handKarten1.get(i).getWert());
			}
			
			System.out.println("------------------------------JokerKartenWert handkarten 2------------------------------");
			for (int i = 0; i < 3; i++){
				System.out.println(handKarten2.get(i).getWert());
			}

		}
		
		
		//Drei Spieler aufteilung
		else if(anzahl==3){
			
		}
	}
	
}
