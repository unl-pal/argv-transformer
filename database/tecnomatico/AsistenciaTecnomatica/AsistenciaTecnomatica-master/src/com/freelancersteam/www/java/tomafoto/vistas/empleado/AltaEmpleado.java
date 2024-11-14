/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancersteam.www.java.tomafoto.vistas.empleado;

import com.freelancersteam.www.java.tomafoto.dominio.Asistencia;
import com.freelancersteam.www.java.tomafoto.dominio.Empleado;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.AsistenciaDaoImp;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.EmpleadoDaoImp;
import com.freelancersteam.www.java.tomafoto.estudiandojmf.mensajero;
import com.freelancersteam.www.java.tomafoto.estudiandojmf.miPlayer;
import com.freelancersteam.www.java.tomafoto.hibernateUtil.Conexion;
import com.sun.imageio.plugins.jpeg.JPEG;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.net.www.content.image.png;

/**
 *
 * @author Leo
 */
public class AltaEmpleado extends javax.swing.JDialog {
//    public static final int MENU =1;
    public static final int GESTOR_EMPLEADO =1;
    
    
    private ImageIcon icono;
    byte[] imgByte;
    private String pathFoto;
    int resp;// respuesta  si agrego o no una foto el empleado,  
    private boolean seleccionofoto=false;
    private int legajo=0;
    private boolean BotonGuardarSelecciono=false;
    private String nombredelarchivo;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIpod btnCancelarOperacion;
    private org.edisoncor.gui.button.ButtonIpod btnGuardar;
    private org.edisoncor.gui.button.ButtonIpod btneEliminar;
    private javax.swing.JFileChooser elegirFichero;
    private javax.swing.JButton jButton1;
    private org.edisoncor.gui.label.LabelMetric labelMetric10;
    private org.edisoncor.gui.label.LabelMetric labelMetric11;
    private org.edisoncor.gui.label.LabelMetric labelMetric12;
    private org.edisoncor.gui.label.LabelMetric labelMetric13;
    private org.edisoncor.gui.label.LabelMetric labelMetric14;
    private org.edisoncor.gui.label.LabelMetric labelMetric15;
    private org.edisoncor.gui.label.LabelMetric labelMetric9;
    private javax.swing.JLabel lblFoto;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtApellido;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtDireccion;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtDni;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtLegajo;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtLocalidad;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtNombre;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtTelefono;
    // End of variables declaration//GEN-END:variables
}
