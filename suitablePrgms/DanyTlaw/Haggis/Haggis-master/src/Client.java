import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class Client {
	
	LoginGUI login;
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	Object inputObject;
	private String lblText;
	
	static int client_ID;
	public static Gameobjekt game;
	public static Chat chat;

	//Methode welche die Informationen des Gegners ladet
	public void ladetGegnerInfo(){
	
		login.getTisch().haggis.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		login.getTisch().Haggisborder.setTitle("Haggis: " + game.getHaeggis().size());
		login.getTisch().haggis.setBorder(login.getTisch().Haggisborder);
		
		
		int anzahlKarten = 0;
		
		//Ladet alle Gegner Informationen
		if(client_ID == 0){
			login.getTisch().setGegnerInfos(game.getSpieler(1).hatBube(), game.getSpieler(1).hatDame(), game.getSpieler(1).hatKoenig());
			login.getTisch().setAnzahlKarten(game.getSpieler(1).getHandKarten().size()-3);
			login.getTisch().lblPunkteGegner.setText("Gegnerische Punktzahl : " +game.getSpieler(1).getPunkte());
			login.getTisch().lblPunkteEigen.setText("Punktzahl : " +game.getSpieler(0).getPunkte());
			
			for (int i = 3; i < game.getSpieler(1).getHandKarten().size(); i++){
				if(game.getSpieler(1).getHandKarten().get(i).getWert() != 0){
					anzahlKarten += 1;
				}
			}
			
			login.getTisch().lblHandkarten.setText("Handkarten : " +anzahlKarten);
			anzahlKarten = 0;
		}
		else if(client_ID == 1){
			login.getTisch().setGegnerInfos(game.getSpieler(0).hatBube(), game.getSpieler(0).hatDame(), game.getSpieler(0).hatKoenig());
			login.getTisch().setAnzahlKarten(game.getSpieler(0).getHandKarten().size()-3);
			login.getTisch().lblPunkteGegner.setText("Gegnerische Punktzahl : " +game.getSpieler(0).getPunkte());
			login.getTisch().lblPunkteEigen.setText("Punktzahl : " +game.getSpieler(1).getPunkte());
				
			for (int i = 3; i < game.getSpieler(0).getHandKarten().size(); i++){
				if(game.getSpieler(0).getHandKarten().get(i).getWert() != 0){
					anzahlKarten += 1;
				}
			}
		
			login.getTisch().lblHandkarten.setText("Handkarten : " +anzahlKarten);
		}
		
	}
	
	
	}
