/*
 * Tablero.java
 */

package ajedrez;

import java.io.*;
import java.util.*;

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
public class Tablero implements Constantes {
        
    // Informacion de estado de un tablero
    
    private Pieza[][] T;
    private byte enroqueBlancas;
    private byte enroqueNegras;
    private boolean enJaque;
    private Posicion alPaso = null;
    private int turno;
    
    // Cosas varias
    
    /**
     * Busca el rey de un color dado en un tablero
     * @param color BLANCO o NEGRO, del rey a buscar
     * @return la posición del rey
     */
    public Posicion buscaRey(int color) {
        Pieza actual;
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                actual = get(i,j);
                if (actual != null && 
                    (actual instanceof Rey) &&
                    actual.getColor() == color)
                {
                    return actual.getPosicion();
                }
            }
        }
        
        throw new IllegalArgumentException("Rey no encontrado!");
    }
    
    /**
     * Genera todos los movimientos válidos para el jugador actual en este tablero
     * @return un ArrayList que contiene los movimientos validos
     */    
    public ArrayList generaMovimientos() {
        ArrayList todas = new ArrayList();
        ArrayList buenas = new ArrayList();
	ArrayList nuevas;
        Pieza actual;
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                if (vacia(i, j) || get(i, j).getColor() != turno) continue;
                nuevas = get(i, j).posiblesMovimientos(this);
		todas.addAll(nuevas);
            }
        }
        
        // depura la lista eliminando las que llevan a jaque        
        Posicion posReyInicial = buscaRey(turno);
        Posicion posRey;
        
        for (int i=0; i<todas.size(); i++) {
            Tablero t = new Tablero(this);
            // mueveSinJaque devuelve la pos. del rey solo si ha cambiado
            posRey = t.mueveSinJaque((Movimiento)todas.get(i));
            if (posRey == null) posRey = posReyInicial;            
            // si el contrario puede NO comer nuestro rey, la jugada es buena
            if ( ! t.amenazada(posRey.col, posRey.fil, t.turno)) {
                buenas.add(todas.get(i));
            }
        }
        
        return buenas;
    }
    
    /** devuelve 'true' si el jugador 'color' puede capturar una pieza
     * contraria (supuestamente) situada en 'p'
     * @param col columna de la posición cuyo estado de amenaza se quiere comprobar
     * @param fil columna de la posición cuyo estado de amenaza se quiere comprobar
     * @param color jugador (BLANCO o NEGRO) que estaría amenazando esa posición
     * @return true si amenazada, false si no
     */
    public boolean amenazada(int col, int fil, int color) {        
        Pieza actual;
        for (int j=0; j<FILS; j++) {
            for (int i=0; i<COLS; i++) {
                actual = get(i, j);
                if (actual != null && 
                    actual.getColor() == color && 
                    actual.puedeComer(this, new Posicion(col, fil))) 
                {
                    return true;
                }
            }
        }
        return false;
    }       
}
