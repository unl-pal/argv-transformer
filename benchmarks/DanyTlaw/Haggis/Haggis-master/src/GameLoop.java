import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;


public class GameLoop extends Thread{

	//attributes
	private Socket socket = null;

	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Object inputObject;
	
	//static attributes
	static int client_ID = 0;
	static int user_ID = 0;
	public static volatile ArrayList<Spieler> userlist = new ArrayList<Spieler>(2);
	public static ArrayList<ObjectOutputStream> outlist = new ArrayList<ObjectOutputStream>(2);

	public void run(){
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			outlist.add(out);
			in = new ObjectInputStream(socket.getInputStream());
		
		}catch (IOException e) {
			System.out.println("Fehler beim erstellen der Streams");
		}	
		
		
		
		while (true) {
			try {
				System.out.println("vor inputObject");
				inputObject = in.readObject();
				System.out.println("nach inputObject");
				//object from User
				if (inputObject instanceof Spieler){
					Spieler spieler = (Spieler) inputObject;
					spieler.setSpieler_ID(user_ID);
					userlist.add(spieler);
					user_ID++;
					out.writeObject(client_ID);
					client_ID++;
					System.out.println("erfolgreich verbunden");
					
					// creating Masterobject
					if (userlist.size() == 2) {
						
						System.out.println("creating master objekt");
						Gameobjekt game = new Gameobjekt(userlist);
						
						//sleep - otherwise complications with Clients (update-problem)
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						
						
						// send Masterobject to Clients
						Iterator<ObjectOutputStream> i = outlist.iterator();
						while (i.hasNext()) {
							i.next().writeObject(game);

						}
					}
				}

					
					
				else if(inputObject instanceof Gameobjekt){

					Gameobjekt game = (Gameobjekt) inputObject;	
					
					
					
					//Wenn einer von beiden Spielern eine leeere Hand hat ist die Runde vorbei und die Logik wird angewandt
					if(game.getSpieler(0).leereHand()){
						
						game.setRundenEnde(true);
						
						
						
						//Damit die Zulezt ausgespielte Karte dem gewinner hinzugefügt werden
						game.getSpieler(0).addGewonneneKarten(game.getAusgespielteKarten());
						
						//Für jede handkarte die noch auf der gegnerischen Hand ist bekommt der Sieger der Runde 5 Punkte
						for(int i = 0; i< game.getSpieler(1).getHandKarten().size(); i++){
							if(game.getSpieler(1).getHandKarten().get(i).getWert()!=0){
								game.getSpieler(0).setPunkte(game.getSpieler(0).getPunkte()+5);
							}
						}
						System.out.println("Karten gegner Punkte: " + game.getSpieler(0).getPunkte());
						//Added dem Sieger der Runde den Haggis hinzu
						game.getSpieler(0).addGewonneneKarten(game.getHaeggis());
						
						System.out.println(" Karten punkte + Haggis: " + game.getSpieler(0).getPunkte());
						
						//Berchnet die Punkte der gewonenen Karten für beide Spieler
						for(int k = 0; k < game.getSpielerList().size(); k++){
							game.getSpieler(k).setPunkte(game.getSpieler(k).berechnePunkte());
							//Nachdem Punkte berechnet wurden muss gewonnene Karten gecleared werden fuer die naechste Runde
							game.getSpieler(k).getGewonneneKarten().clear();
						}
						
						
						
						//Der Spieler der keine Karten mehr auf der Hand hat bekommt die Punkte der Wette
						game.getSpieler(0).setPunkte(game.getSpieler(0).getPunkte() + game.getSpieler(0).getWette());

					}
					else if(game.getSpieler(1).leereHand()){
							
						game.setRundenEnde(true);
						
						//Damit die Zulezt ausgespielte Karte dem gewinner hinzugefügt werden
						game.getSpieler(1).addGewonneneKarten(game.getAusgespielteKarten());
													
						//Für jede handkarte die noch auf der gegnerischen Hand ist bekommt der Sieger der Runde 5 Punkte
						for(int i = 0; i< game.getSpieler(0).getHandKarten().size(); i++){
							
							if(game.getSpieler(0).getHandKarten().get(i).getWert()!=0){
								game.getSpieler(1).setPunkte(game.getSpieler(1).getPunkte()+5);
								
							}
						}
						
						//Added dem Sieger der Runde den Haggis hinzu
						game.getSpieler(1).addGewonneneKarten(game.getHaeggis());
						
						//Berchnet die Punkte der gewonenen Karten für beide Spieler
						for(int k = 0; k < game.getSpielerList().size(); k++){
							game.getSpieler(k).setPunkte(game.getSpieler(k).berechnePunkte());
							//Nachdem Punkte berechnet wurden muss gewonnene Karten gecleared werden fuer die naechste Runde
							game.getSpieler(k).getGewonneneKarten().clear();
						}
							
						//Der Spieler der keine Karten mehr auf der Hand hat bekommt die Punkte der Wette
						game.getSpieler(1).setPunkte(game.getSpieler(1).getPunkte() + game.getSpieler(1).getWette());
						
						//Wenn ein Spieler die abgemachten Punkte erreicht hat, hat er gewonnen
						

					}
						
					//Wenn ein Spieler eine leere hand hat ist die Runde beendet, nun muss ueberprueft werden ob ein Spieler die Siegespunkte erreicht hat
					if(game.getRundenEnde()){
						
						//Wenn ein Spieler Siegespunkte erreicht hat und mehr Punkte wie der Gegner hat, ist er der Sieger
						if(game.getSpieler(0).getPunkte() >= game.getSpieler(0).getSiegesPunkte() && game.getSpieler(0).getPunkte() > game.getSpieler(1).getPunkte()){
							game.getSpieler(0).setSieger(true);
							game.setSpielBeendet(true);
						}						
						else if(game.getSpieler(1).getPunkte() >= game.getSpieler(1).getSiegesPunkte() && game.getSpieler(1).getPunkte() > game.getSpieler(0).getPunkte()){
							game.getSpieler(1).setSieger(true);
							game.setSpielBeendet(true);
						//Wurde kein Sieger ermittelt geht das Spiel weiter
						}else{
							game.setNeueRunde(true);
							game.setRundenEnde(false);
							game.erstelleDeck();
							game.setRunde(0);
						}
					}
					
					try {
						Thread.sleep(100);
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//Setzt die am zugvaribale fÃ¼r zwei spieler
					if(game.getSpielerList().size()==2){
						if(game.getSpieler(0).getAmZug()){
							game.getSpieler(0).setAmZug(false);		
							game.getSpieler(1).setAmZug(true);
						}else if(game.getSpieler(1).getAmZug()){
							game.getSpieler(0).setAmZug(true);
							game.getSpieler(1).setAmZug(false);
						}
					}
	
	
					//Uebergiebt dem nich passenden Spieler alle Karten da er der hÃ¶chste Stich hatte
					if(game.getSpieler(0).getPassen()){
						//Wenn Spieler passt ist eine Runde beendet Rundenzaehler wird erhöt
						game.setRunde(game.getRunde()+1);
						//Wenn Spieler passt ist eine Runde beendet Rundenzaehler wird erhöt
						if(game.getBombe()){
							game.getSpieler(0).addGewonneneKarten(game.getAusgespielteKarten());
							game.setBombe(false);
						//Wenn Spieler passt ist eine Runde beendet Rundenzaehler wird erhöt
						}else{
							game.getSpieler(1).addGewonneneKarten(game.getAusgespielteKarten());
						}
						
						game.getSpieler(0).setPassen(false);
						game.getAusgespielteKarten().clear();
						game.getFeldkarten().clear();
					}
					else if(game.getSpieler(1).getPassen()){
						//Wenn Spieler passt ist eine Runde beendet Rundenzaehler wird erhöt
						game.setRunde(game.getRunde()+1);
						//Wenn Spieler passt ist eine Runde beendet Rundenzaehler wird erhöt
						if(game.getBombe()){
							game.getSpieler(1).addGewonneneKarten(game.getAusgespielteKarten());
							game.setBombe(false);
						//Wenn Spieler passt ist eine Runde beendet Rundenzaehler wird erhöt
						}else{
							game.getSpieler(0).addGewonneneKarten(game.getAusgespielteKarten());
						}
						game.getSpieler(1).setPassen(false);
						game.getAusgespielteKarten().clear();
						game.getFeldkarten().clear();
					}
					
					Iterator<ObjectOutputStream> i = outlist.iterator();
					while (i.hasNext()) {
						i.next().writeObject(game);
						
					}
				}
				else if(inputObject instanceof Chat){
					Chat chat = (Chat) inputObject;
						
						
					System.out.println(chat.getMessage());
					Iterator<ObjectOutputStream> i = outlist.iterator();
					while (i.hasNext()) {
						try {
							Thread.sleep(1000);
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
						i.next().writeObject(chat);
							
					}
						
				}
			}catch (NullPointerException e) {
				System.out.println("Client hat Verbindung abgebrochen.");
				userlist.clear();
				break;
			}catch (IOException e) {
				System.out.println("In/Out Streams geschlossen.");
				userlist.clear();
				break;
			} catch (ClassNotFoundException e) {
				System.out.println("Objekt konnte nicht gelesen werden!");
				break;
			}
		}	
	}
}
