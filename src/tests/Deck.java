package tests;

import java.util.Random;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Diese Klasse enthaelt die Logik eines Deckes
public class Deck {
	
	
	public boolean spieler1 = true;
	
	private int kartenBreite = 60;
	private int kartenHoehe = 100;
	
	//Konstruktor welche ein Deck mit allen 54 Karten vom Typ Card erstellt
	public Deck(){
		
		Random rand = new Random();
		String pfad = System.getProperty("user.dir") + "//images//";
		
		Dimension screenSize;
		 double width = rand.nextFloat();
		 double height = rand.nextFloat();
		
		if(height > 1000 && width > 1300){
			 kartenBreite = 100;
			 kartenHoehe = 150;
		}
		

		
	}
	
	//Methode welche die Karten nach den Regeln auf zwei Spieler aufteilt
	public void aufteilen(int anzahl){
		
		
		
		
		
		Random rand = new Random();
		//Zwei Spieler aufteilung
		if(anzahl==2){
			
			int j = 0;
			while(j < rand.nextInt()){
				
				if(rand.nextBoolean()){
					if(rand.nextBoolean()){
					}
				}
				else{
					j++;
				}
			}
			
			//Schleife welche einen Buben entfernt
			for(int i =0;i < rand.nextInt();i++){
				if(rand.nextBoolean()){
					break;
				}
			}
			//schleife welche eine Dame entfernt
			for(int i =0;i < rand.nextInt();i++){
				if(rand.nextBoolean()){
					break;
				}
			}
			//Schleife welche einen Koenig entfernt
			for(int i =0;i < rand.nextInt();i++){
				if(rand.nextBoolean()){
					break;
				}
			}
			


			//While Schleife welche beiden Spielern einen Buben gibt
			int k = 0;
			while(true){
				if(spieler1){
					if(rand.nextBoolean()){
						spieler1 = false;
						k--;
					}
				}
				else if(!spieler1){
					if(rand.nextBoolean()){
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
					if(rand.nextBoolean()){
						spieler1 = false;
						k--;
					}
				}
				else if(!spieler1){
					if(rand.nextBoolean()){
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
					if(rand.nextBoolean()){
						spieler1 = false;
						k--;
					}
				}
				else if(!spieler1){
					if(rand.nextBoolean()){
						spieler1 = true;
						break;
					}
				}
				k++;
			}
			
			
			//Schleife in der 14 mal eine Zufallszahl gemacht wird zwischen

			
			for(int i = 0; rand.nextInt()<28;i++){
				//Variable welche dafuer sorgt das die Karten abwechselnd verteilt werden
				
				
				int decZufallZahl = (int) (rand.nextInt()+1);
				int zufallsZahl = rand.nextInt() - 1;
				if(spieler1){
					spieler1 = false;
				}
				else if(!spieler1){
					spieler1=true;
				}
				
			}
			for (int i = 0; rand.nextInt() < 3; i++){
			}
			
			for (int i = 0; rand.nextInt() < 3; i++){
			}

		}
		
		
		//Drei Spieler aufteilung
		else if(anzahl==3){
			
		}
	}
	
	//Getters und Setters
	public Object getHandKarten1(){
		return new Object();
	}
	
	public Object getHandKarten2(){
		return new Object();
	}
	
	public Object getHand(int i){
		return new Object();
	}
	
	public Object getHaeggis(){
		return new Object();
	}
	
}
