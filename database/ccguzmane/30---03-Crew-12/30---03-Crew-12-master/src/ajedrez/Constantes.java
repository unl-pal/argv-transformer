/*
 * Constantes.java
 *
 */

package ajedrez;

/**
 * Interfaz que contiene las constantes a usar en este juego de ajedrez
 * Las piezas tienen asignadas un caracter representativo, donde el caracter
 * de las blancas se esribe en minúsculas y el de las negras es el mismo pero
 * en mayúsculas.
 *
 * @author  mf
 */
public interface Constantes {

    /** casilla vacia */
    static final char VACIO = '-';

    /** representacion de un peon de blancas */
    static final char PEON = 'p';
    
    /** representacion de un caballo de blancas */
    static final char CABALLO = 'c';
    
    /** representacion de un alfil de blancas */
    static final char ALFIL = 'a';
    
    /** representacion de una torre de blancas */
    static final char TORRE = 't';
    
    /** representacion de una (¿la?) reina blanca */
    static final char REINA = 'q';
    
    /** representacion del rey blanco */
    static final char REY = 'r';    

    /** enroque corto valido */
    static final byte ENROQUE_CORTO=1;
    
    /** enroque largo valido */
    static final byte ENROQUE_LARGO=2;
    
    /** no es valido ningun enroque */
    static final byte ENROQUE_NO=0;
    
    /** no es valido ningun enroque */
    static final byte ENROQUE_AMBOS=(ENROQUE_CORTO|ENROQUE_LARGO);
    
    /** columnas en un tablero */
    static final int COLS=8;
    
    /** filas en un tablero */
    static final int FILS=8;
    
    /** el color blanco (se usa para piezas, para turno, y para mov. de peones) */
    static final int BLANCO=1;
    
    /** el color negro (usado en piezas, en turnos, y en mov. de peones) */
    static final int NEGRO=-1;    
    
    /** posicion inicial del tablero, como char de piezas en A1, B1 ... H8 */
    static final String posInicial=
        "tcaqractpppppppp--------------------------------PPPPPPPPTCAQRACT";    
    
}
