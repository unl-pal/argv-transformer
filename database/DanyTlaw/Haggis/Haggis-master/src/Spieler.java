import java.io.Serializable;
import java.util.ArrayList;


public class Spieler implements Serializable {

	/**
	 * 
	 */

	//Spieler informationen
	private String spielerName;
	private int spieler_ID;
	private ArrayList<Card> handKarten;
	private int punkte;
	private boolean amZug;
	private boolean passen = false;
	private int siegesPunkte;
	private boolean sieger = false;
	private int wette;
	private volatile boolean gewettet = false;
	
	public ArrayList<Card> gewonneneKarten = new ArrayList<Card>();
	
}
