import java.io.Serializable;
import java.util.ArrayList;


public class Gameobjekt implements Serializable {

	private ArrayList<Spieler> spieler;
	private ArrayList<Card> feldkarten = new ArrayList<Card>();
	private ArrayList<Card> ausgespielteKarten = new ArrayList<Card>();
	private ArrayList<Card> haeggis = new ArrayList<Card>();
	
	private boolean neueRunde;
	private boolean spielBeendet;
	private boolean bombe = false;
	private boolean wettenAbwicklung;
	private boolean rundenEnde = false;
	
	private int runde = 0;
	
}
