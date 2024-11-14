package util;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rescuecore2.standard.entities.Building;
import rescuecore2.standard.entities.Hydrant;
import rescuecore2.standard.entities.Refuge;
import rescuecore2.standard.entities.Road;
import rescuecore2.standard.entities.StandardEntity;
import rescuecore2.standard.entities.StandardWorldModel;
import rescuecore2.worldmodel.EntityID;

/* LEGENDA MAPA:         
 *   
 *               idNorte    
 *            ______*______
 *           | 4-NO | 1-NE |
 *  idOeste *|-------------|* idLeste 
 *           | 3-So | 2-SE |
 *           ---------------
 *                  *
 *                 idSul
 *    
 *     idNorte = MelhorDistancia(CentroX,MaxY)
 *     idSul   = MelhorDistancia(CentroX,MinY)
 *     idLeste = MelhorDistancia(CentroY,MaxX)
 *     idOeste = MelhorDistancia(CentroY,MinX)
 *               
 */
public class MASLABSectoring {

	private static double coordinate_MaxX = 0;
	private static double coordinate_MaxY = 0;
	private static double coordinate_MinX = 0;
	private static double coordinate_MinY = 0;
	private static double coordinate_CenterX = 0;
	private static double coordinate_CenterY = 0;
	
	//aliases for the agent types
	public static final int AMBULANCE_TEAM = 1;
	public static final int FIRE_BRIGADE = 2;
	public static final int POLICE_FORCE = 3;
	public static final int UNDEFINED_PLATOON = 0;
	
	private List<EntityID> Avenue_NtoS;
	private List<EntityID> Avenue_LtoO;

	public Map<EntityID, Set<EntityID>> MapSetor1 = new HashMap<EntityID, Set<EntityID>>();
	public Map<EntityID, Set<EntityID>> MapSetor2 = new HashMap<EntityID, Set<EntityID>>();
	public Map<EntityID, Set<EntityID>> MapSetor3 = new HashMap<EntityID, Set<EntityID>>();
	public Map<EntityID, Set<EntityID>> MapSetor4 = new HashMap<EntityID, Set<EntityID>>();
	public Map<EntityID, Set<EntityID>> MapPrincipal = new HashMap<EntityID, Set<EntityID>>();
	public Map<EntityID, List<EntityID>> MapSecundarias = new HashMap<EntityID, List<EntityID>>();

	private static EntityID idNorte;
	private static EntityID idSul;
	private static EntityID idLeste;
	private static EntityID idOeste;

	private Polygon SetorNordeste = new Polygon();
	private Polygon SetorSudeste = new Polygon();
	private Polygon SetorSudoeste = new Polygon();
	private Polygon SetorNoroeste = new Polygon();

	private StandardWorldModel model;

	MASLABBFSearch search;
	
	List<EntityID> roadIDs = new ArrayList<EntityID>();
	List<EntityID> buildingIDs = new ArrayList<EntityID>();
	List<EntityID> refugeIDs = new ArrayList<EntityID>();
	List<EntityID> hydrantIDs = new ArrayList<EntityID>();

	private void debug() {
		Integer a[] = { 36495, 36202, 36205, 36373, 36207, 36183, 35966, 35963,
				35960, 35958, 35954, 35951, 35948, 35946, 35942, 35939, 35936,
				35932, 35930, 4731, 2382, 7359, 4524, 34584, 6450, 33986,
				33987, 33976, 34513, 5793, 34462, 9051, 4389, 6018, 34565,
				2000, 6378, 34562, 9375, 5883, 3345, 6720, 1976, 2535, 7206,
				892, 3471, 8790, 5775, 1504, 4146, 34753 };
		Integer b[] = { 30883, 30884, 30859, 30860, 8610, 4668, 7458, 3183,
				445, 3840, 7809, 2922, 4695, 7098, 32572, 32571, 2051, 8250,
				31201, 34241, 34240, 4461, 6567, 1596, 3678, 5127, 2643, 35344,
				35345, 2841, 34222, 34223, 34224, 3021, 32767, 32765, 34279,
				8727, 2000, 34565, 6018, 4389, 9051, 34462, 5793, 34513, 33976,
				33987, 2508, 7215, 25835, 34654, 33968, 8925, 5766, 4128, 4173,
				352, 4155, 33357, 4956, 33169, 33179, 33190, 33191, 33234,
				33282, 33431, 33447 };

		List<Integer> Lista1 = Arrays.asList(a);
		List<Integer> Lista2 = Arrays.asList(b);

		List<Integer> ListaMapa = Arrays.asList(4128, 6567, 4146, 4389, 4155,
				9051, 34654, 33282, 32571, 8790, 32572, 7098, 4668, 2508, 3471,
				7359, 892, 4956, 1596, 6378, 7809, 4695, 4461, 33357, 3021,
				4173, 2535, 25835, 5127, 34584, 8727, 36495, 34565, 352, 34562,
				4731, 8250, 33447, 33169, 33179, 35932, 31201, 33987, 33986,
				35930, 36183, 6450, 2051, 4524, 9375, 34241, 34240, 35958,
				2000, 35954, 445, 3678, 35966, 36205, 36207, 35963, 35345,
				35344, 36202, 35960, 35942, 3183, 33431, 35939, 1504, 33191,
				35936, 33190, 8925, 5883, 34513, 35951, 35948, 34753, 34279,
				35946, 2841, 5766, 33234, 2382, 2922, 5775, 7458, 1976, 6720,
				6018, 7215, 30884, 3345, 7206, 30883, 36373, 2643, 5793, 34224,
				3840, 34462, 33976, 30860, 30859, 34223, 34222, 32767, 8610,
				32765, 33968);

		// Verifica se todos da listaavenidas estão na listamapa

		for (Integer v : Lista1) {
			if (!ListaMapa.contains(v)) {
				System.out.println(v + " não está na lista do mapa");
			}
		}
		for (Integer v : Lista2) {
			if (!ListaMapa.contains(v)) {
				System.out.println(v + " não está na lista do mapa");
			}
		}

		for (Integer v : ListaMapa) {
			if (!Lista1.contains(v) && !Lista2.contains(v)) {
				System.out.println(v + " não está na lista da avenida");
			}
		}

		int[] xs = SetorNordeste.xpoints;
		int[] ys = SetorNordeste.ypoints;
		System.out.println("Nordeste");
		for (int i = 0; i < xs.length; i++) {
			System.out.println(xs[i] + " " + ys[i] + " - " + i);
		}

		xs = SetorSudeste.xpoints;
		ys = SetorSudeste.ypoints;
		System.out.println("Sudeste");
		for (int i = 0; i < xs.length; i++) {
			System.out.println(xs[i] + " " + ys[i] + " - " + i);
		}

		xs = SetorSudoeste.xpoints;
		ys = SetorSudoeste.ypoints;
		System.out.println("Sudoeste");
		for (int i = 0; i < xs.length; i++) {
			System.out.println(xs[i] + " " + ys[i] + " - " + i);
		}

		xs = SetorNoroeste.xpoints;
		ys = SetorNoroeste.ypoints;
		System.out.println("Noroeste");
		for (int i = 0; i < xs.length; i++) {
			System.out.println(xs[i] + " " + ys[i] + " - " + i);
		}
	}
}
