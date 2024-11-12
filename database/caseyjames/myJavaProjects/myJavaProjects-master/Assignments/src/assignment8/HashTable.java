package assignment8;

import java.util.Collection;

/**
 * An abstract class facilitating the implementation of a concrete hash table.
 *
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public abstract class HashTable implements Set<String> {
    /**
     * FILL IN MEMBER VARIABLES!
     *
     * Any member variables that you would maintain in both your QuadProbeHashTable and
     * your ChainingHashTable (such as, say, a size variable) probably belong here in
     * the parent class. Should the variable(s) be public, private, or protected?
     */
    protected int size, capacity, collisions;
    protected HashFunctor hasher;
}
