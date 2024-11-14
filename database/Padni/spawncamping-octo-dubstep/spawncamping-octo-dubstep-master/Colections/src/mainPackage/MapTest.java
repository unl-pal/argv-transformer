package mainPackage;

import java.util.HashMap;
import java.util.Map;

/*
 * Program testujacy Mapy w javie 
 * @author Dariusz Brzostek
 * @version 0.0.1
 */

public class MapTest {
	
	Map<Integer, Person> staffa = new HashMap<Integer, Person>();
	
	

	Person pierwszy = new Person("Adam", "Kowslski", 21);
	Person druga = new Person("Karolina", "Tworek", 25);
	Person trzeci = new Person("Dariusz", "Brzostek", 25);
	
	Person staraWartosc1 = staffa.put(1, pierwszy);
	Person staraWartosc2 = staffa.put(2, druga);
	Person staraWartosc3 = staffa.put(3, trzeci);
	
}
