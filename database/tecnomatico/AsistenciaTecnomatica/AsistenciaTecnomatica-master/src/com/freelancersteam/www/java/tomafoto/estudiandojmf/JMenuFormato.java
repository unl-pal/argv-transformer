/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.freelancersteam.www.java.tomafoto.estudiandojmf;

import com.freelancersteam.www.java.tomafoto.vistas.main.Camara;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Cmop
 */
public class JMenuFormato extends JMenuItem implements ActionListener{

    private int ancho;
    private int alto;
    private JPanel modificable;
    private Camara padre;
    private String dispositivo;
    private int ordinal;
}