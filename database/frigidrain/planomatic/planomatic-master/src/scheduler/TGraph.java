package scheduler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A graph that keeps a copy of its own transpose.
 * @author duncan
 *
 * @param <T>
 */

public class TGraph<T> extends Graph<T> {
	private HashMap<T, List<T>> transpose;
}
