/**
 * JuegoApplet
 *
 * Anima un Link y con las flechas se puede mover
 *
 * @author Antonio Mejorado
 * @version 2.0 2015/1/15
 */
 
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;

public class JuegoApplet extends Applet implements Runnable, KeyListener {
    // Se declaran las variables y objetos
    // direccion en la que se mueve el Link
    // 1-arriba,2-abajo,3-izquierda y 4-derecha
    private int iDireccion;                    
    private AudioClip aucSonidoLink;     // Objeto AudioClip sonido Elefante
    private Animal aniLink;         // Objeto de la clase Elefante
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    Image imaImagenLinkDer; //Link Derecho
    Image imaImagenLinkDerGolpe;  
    Image imaImagenLinkIzq; //Link Izquierdo
    Image imaImagenLinkIzqGolpe;
    Image imaImagenLinkArriba; //Link Arriba
    Image imaImagenLinkArribaGolpe;
    Image imaImagenLinkAbajo; //Link Abajo
    Image imaImagenLinkAbajoGolpe;
    int VelY;
    int VelX;
    boolean bolGolpeDer;
    boolean bolGolpeIzq;
    boolean bolGolpeArriba;
    boolean bolGolpeAbajo;
    int tiempoCambioSprite;
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion del objeto aniLink 
     * 
     */
    public void actualiza(){
        //Dependiendo de la Direccion de Link es hacia donde se mueve.
        switch(iDireccion) {
            case 1: { //se mueve hacia arriba
                if (VelY<0){
                    aniLink.setImagen(imaImagenLinkArriba);    
                }
                aniLink.setY(aniLink.getY() + VelY);
                break;    
            }
            case 2: { //se mueve hacia abajo
                if (VelY>0){
                    aniLink.setImagen(imaImagenLinkAbajo);
                }
                aniLink.setY(aniLink.getY() + VelY);
                break;    
            }
            case 3: { //se mueve hacia izquierda
                if (VelX<0){
                    aniLink.setImagen(imaImagenLinkIzq);    
                }
                aniLink.setX(aniLink.getX() + VelX);
                break;    
            }
            case 4: { //se mueve hacia derecha
                if (VelX>0){
                    aniLink.setImagen(imaImagenLinkDer);    
                }
                aniLink.setX(aniLink.getX() + VelX);
                break;    	
            }
        }
        //Los siguientes if y else if son los que verifican si hay colision
        //pues si existe, cambia la imagen a la correspondiente
        if (bolGolpeDer){
            aniLink.setImagen(imaImagenLinkIzqGolpe);
            tiempoCambioSprite++;
            if(tiempoCambioSprite==10){ //Si es 10, entonces procedo a cambiar
                                        //la imagen a la normal
               aniLink.setImagen(imaImagenLinkIzq);
               bolGolpeDer=false;
               tiempoCambioSprite=0;
            }
        }
        
        else if (bolGolpeIzq){
            aniLink.setImagen(imaImagenLinkDerGolpe);
            tiempoCambioSprite++;
            if(tiempoCambioSprite==10){
               aniLink.setImagen(imaImagenLinkDer);
               bolGolpeIzq=false;
               tiempoCambioSprite=0;
            }
        }
        
        else if (bolGolpeArriba){
            aniLink.setImagen(imaImagenLinkAbajoGolpe);
            tiempoCambioSprite++;
            if(tiempoCambioSprite==10){
               aniLink.setImagen(imaImagenLinkAbajo);
               bolGolpeArriba=false;
               tiempoCambioSprite=0;
            }
        }
        
        else if (bolGolpeAbajo){
            aniLink.setImagen(imaImagenLinkArribaGolpe);
            tiempoCambioSprite++;
            if(tiempoCambioSprite==10){
               aniLink.setImagen(imaImagenLinkArriba);
               bolGolpeAbajo=false;
               tiempoCambioSprite=0;
            }
        }
        
        
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision del objeto Link
     * con las orillas del <code>Applet</code>.
     * 
     */
    public void checaColision(){
        //Colision del Link con el Applet dependiendo a donde se mueve.
     //Si la velocidad vertical es menor a cero, se esta moviendo hacia 
        //arriba
        if (VelY<0){
            if(aniLink.getY() < 0) { // y esta pasando el limite
                    iDireccion = 2;     // se cambia la direccion para abajo
                    VelY=-VelY; //Hago que se regrese con la misma velocidad
                                //pero para en direccion opuesta
                    bolGolpeArriba=true;
                    aucSonidoLink.play();
            
                }
            }
            
        //Si la velocidad vertical es mayor a cero, se esta moviendo hacia 
        //abajo
        if (VelY>0){
            if(aniLink.getY() + aniLink.getAlto() > getHeight()) { // y esta pasando el limite
                    iDireccion = 1;     // se cambia la direccion para arriba
                    VelY=-VelY; //Hago que se regrese con la misma velocidad
                                //pero para en direccion opuesta
                    bolGolpeAbajo=true;
                    aucSonidoLink.play();
            
                }
            }
            
        //Si la velocidad horizontal es mayor a cero, se esta moviendo hacia 
        //la derecha
        if (VelX>0){
            if(aniLink.getX() + aniLink.getAncho() > getWidth()) { // y esta 
                                                            //pasando el limite
                    iDireccion = 3;     // se cambia la direccion para izquierda
                    VelX=-VelX; //Hago que se regrese con la misma velocidad, 
                                //pero para en direccion opuesta
                    bolGolpeDer=true;
                    aucSonidoLink.play();
            
                }
            }
            
        //Si la velocidad horizontal es menor a cero, se esta moviendo hacia 
        //la izquierda
        if (VelX<0){
            if(aniLink.getX() < 0) { // y esta pasando el limite
                    iDireccion = 4;     // se cambia la direccion para la derecha
                    VelX=-VelX; //Hago que se regrese con la misma velocidad
                                //pero para en direccion opuestassss
                    bolGolpeIzq=true;
                    aucSonidoLink.play();
            
                }
            }
        
       
    }
}