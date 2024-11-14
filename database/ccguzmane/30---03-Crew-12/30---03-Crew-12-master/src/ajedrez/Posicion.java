/*
 * Posicion.java
 */

package ajedrez;

/**
 * Una posicion en ajedrez, dada por una fila y una columna
 * Para facilitar su uso, los contenidos de una posicion no se pueden 
 * cambiar una vez creada. Por tanto, no hay inconvenientes en permitir
 * acceso publico a sus miembros.
 *
 * @author  mf
 */
public class Posicion {
        
    // Informacion de estado de una posicion
    
    /** columna */
    public final byte col;

    /** fila */
    public final byte fil;
}
