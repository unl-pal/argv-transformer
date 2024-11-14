/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancersteam.www.java.tomafoto.vistas.empleado;

import com.freelancersteam.www.java.tomafoto.dominio.Empleado;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.EmpleadoDaoImp;
import com.freelancersteam.www.java.tomafoto.util.Constantes;
import com.freelancersteam.www.java.tomafoto.vistas.empleado.AltaEmpleado;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joel
 */
public class GestorEmpleado extends javax.swing.JDialog {
    public static final int VENTANA_GESTOR_ASISTENCIA=1;
    public static final int MENU=2;
    public   boolean isModificar = false;// paramentro global uso: para ver si se presiono un boton agregar o moficar
                                         // sirve para configuarar ventnana infomracion  empleado
    
    
    private DefaultTableModel modelo;
    private List<Empleado> listaEmpleado;
    private boolean seleccionado;
    private int legajo;
    int quienloyamo;
    java.awt.Frame parent;// indica quien es el padre. me sirve para pasar el icono de la aplcacion
  //  EmpleadoDao empleados ;
   ImageIcon icono;; // correspondiente a la im agen del usuario
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIpod btnCancelarOperacion;
    private org.edisoncor.gui.button.ButtonIpod btnModificar;
    private org.edisoncor.gui.button.ButtonIpod btnNuevo;
    private org.edisoncor.gui.button.ButtonIpod btnReporte;
    private org.edisoncor.gui.button.ButtonIpod btnSeleccion;
    private org.edisoncor.gui.button.ButtonIpod btnSeleccion2;
    private org.edisoncor.gui.comboBox.ComboBoxRound comboBoxRound1;
    private javax.swing.JFileChooser elegirFoto;
    private javax.swing.JScrollPane jScrollPane2;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTable tblEmpleado;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtEmpleado;
    // End of variables declaration//GEN-END:variables
    

     
}
