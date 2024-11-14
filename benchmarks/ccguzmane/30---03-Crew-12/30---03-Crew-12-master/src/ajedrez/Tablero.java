/*
 * Tablero.java
 */

/** filtered and transformed by PAClab */
package ajedrez;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Representa un tablero de ajedrez. Las operaciones principales son
 * <ul>
 * <li>comprobar estado:</li><ul>
 * <li>pieza en posicion x,y: get()</li>
 * <li>jaque del jugador actual: isEnJaque()</li>
 * <li>enroque: getEnroqueBlancas, getEnroqueNegras()</li></ul>
 * </li>
 * <li>generar jugadas: generaMovimientos()</li>
 * <li>mover: mueve()</li>
 * </ul>
 *
 * @author  mf
 */
public class Tablero {
        
    // Informacion de estado de un tablero
    
    /**
     * Busca el rey de un color dado en un tablero
     * @param color BLANCO o NEGRO, del rey a buscar
     * @return la posición del rey
     */
    /** PACLab: suitable */
	 public Object buscaRey(int color) {
        for (int j=0; j<Verifier.nondetInt(); j++) {
            for (int i=0; i<Verifier.nondetInt(); i++) {
                if (Verifier.nondetBoolean() &&
                    Verifier.nondetInt() == color)
                {
                    return new Object();
                }
            }
        }
        
        throw new IllegalArgumentException("Rey no encontrado!");
    }
    
    /**
     * Genera todos los movimientos válidos para el jugador actual en este tablero
     * @return un ArrayList que contiene los movimientos validos
     */    
    /** PACLab: suitable */
	 public Object generaMovimientos() {
        int turno = Verifier.nondetInt();
		for (int j=0; j<Verifier.nondetInt(); j++) {
            for (int i=0; i<Verifier.nondetInt(); i++) {
                if (vacia(i, j) || Verifier.nondetInt() != turno) continue;
            }
        }
        
        for (int i=0; i<Verifier.nondetInt(); i++) {
            if (posRey == null) {
			}            
            // si el contrario puede NO comer nuestro rey, la jugada es buena
            if ( Verifier.nondetBoolean()) {
            }
        }
        
        return new Object();
    }
    
    /** devuelve 'true' si el jugador 'color' puede capturar una pieza
     * contraria (supuestamente) situada en 'p'
     * @param col columna de la posición cuyo estado de amenaza se quiere comprobar
     * @param fil columna de la posición cuyo estado de amenaza se quiere comprobar
     * @param color jugador (BLANCO o NEGRO) que estaría amenazando esa posición
     * @return true si amenazada, false si no
     */
    /** PACLab: suitable */
	 public boolean amenazada(int col, int fil, int color) {        
        for (int j=0; j<Verifier.nondetInt(); j++) {
            for (int i=0; i<Verifier.nondetInt(); i++) {
                if (Verifier.nondetBoolean() && 
                    Verifier.nondetInt() == color && 
                    actual.puedeComer(this, new Posicion(col, fil))) 
                {
                    return true;
                }
            }
        }
        return false;
    }       
}
