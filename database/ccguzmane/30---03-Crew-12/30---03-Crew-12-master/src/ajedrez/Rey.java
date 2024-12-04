/*
 * Rey.java
 */

package ajedrez;

import java.util.*;

/**
 * Esta clase representa un Rey en el juego del ajedrez. También contiene
 * constantes relativas a enroques
 * @author mf
 */
public class Rey extends Pieza {        
    
    // constantes relativas al enroque
    
    /** columna de la torre en un enroque corto */
    public static final int colTorreCorto = COLS-1;

    /** columna de destino del rey en un enroque corto */
    public static final int colDestCorto = COLS-2;

    /** columna de paso del rey/destino de la torre en el enroque corto */
    public static final int colMediaCorto = COLS-3;
        
    /** columna de la torre en un enroque corto */
    public static final int colTorreLargo = 0;
    
    /** columna de destino del rey en un enroque corto */
    public static final int colDestLargo = 2;
    
    /** columna de paso del rey/destino de la torre en el enroque corto */
    public static final int colMediaLargo = 3;
    
    // array constante con los posibles movimientos de un rey    
    private static int[][] movs = 
        {{1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}, {-1,1}, {0,1}};
}
