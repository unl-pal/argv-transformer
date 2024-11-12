package assignment6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * An interface for representing a sorted set of generically-typed items. By definition, a set contains no duplicate
 * items. The items are ordered using their natural ordering (i.e., each item must be Comparable). Note that this
 * interface is much like Java's Set and SortedSet, but simpler.
 *
 * @author Paymon Saebi
 */
public interface SortedSet<Type extends Comparable<? super Type>> {
}
