/*
 * Movimiento.java
 *
 */

package ajedrez;

/**
 * Esta clase representa un movimiento o jugada de ajedrez. Sólo contiene origen,
 * destino, y si el movimiento supone una coronación, la ficha a la que se
 * promociona el peón.
 * @author mf
 */
public class Movimiento implements Constantes, Comparable {
    
    private Posicion origen;
    private Posicion destino;
    private char promocion;            
}
