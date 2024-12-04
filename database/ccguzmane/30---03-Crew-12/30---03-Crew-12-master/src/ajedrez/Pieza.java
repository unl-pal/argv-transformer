/*
 * Pieza.java
 *
 */

package ajedrez;

import java.util.*;

/** 
 * Una pieza en el juego del ajedrez.
 * @author mf
 */
public abstract class Pieza implements Constantes {
    
    // Estado interno de una pieza
    
    /** la posicion de la pieza */
    protected Posicion p;
    
    /** el color de la pieza (BLANCO o NEGRO */
    protected int color;
}
