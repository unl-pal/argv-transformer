/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Camara.java
 *
 * Created on 11/06/2010, 11:53:04 PM
 */

package com.freelancersteam.www.java.tomafoto.vistas.main;

/**
 *
 * @author Cmop
 */
import com.freelancersteam.www.java.tomafoto.vistas.administrador.Login;
import com.freelancersteam.www.java.tomafoto.vistas.empleado.GestorEmpleado;
import com.freelancersteam.www.java.tomafoto.vistas.empleado.AltaEmpleado;

import com.freelancersteam.www.java.tomafoto.dominio.Asistencia;
import com.freelancersteam.www.java.tomafoto.dominio.Empleado;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.AsistenciaDaoImp;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.EmpleadoDaoImp;
import com.freelancersteam.www.java.tomafoto.estudiandojmf.eventos;
import com.freelancersteam.www.java.tomafoto.estudiandojmf.jmfVideo;
import com.freelancersteam.www.java.tomafoto.estudiandojmf.miPlayer;
import com.freelancersteam.www.java.tomafoto.util.Constantes;
import com.freelancersteam.www.java.tomafoto.util.EmpleadoUtil;
import com.freelancersteam.www.java.tomafoto.util.FechaUtil;


import com.freelancersteam.www.java.tomafoto.vistas.administrador.GestorAdministriador;
import com.freelancersteam.www.java.tomafoto.vistas.asistencia.BorrarAsistencia;
import com.freelancersteam.www.java.tomafoto.vistas.asistencia.GestorAsistencia;
import com.freelancersteam.www.java.tomafoto.vistas.empresa.AltaEmpresa;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Player;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


public class Camara extends javax.swing.JFrame{
    jmfVideo b = new jmfVideo();

    private Player p1;
    private DefaultTableModel modelo;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIpod btnIngresar;
    private org.edisoncor.gui.varios.ClockDigital clockDigital2;
    private org.edisoncor.gui.varios.ClockFace clockFace2;
    private org.edisoncor.gui.comboBox.ComboBoxRound cmbElegir;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelMetric labelMetric2;
    private org.edisoncor.gui.label.LabelCustom lblFecha;
    private javax.swing.JLabel lblNameSistema;
    private javax.swing.JLabel lbliconEmpresa;
    private javax.swing.JMenu mnuAcerca;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenu mnuAsistencia;
    private javax.swing.JMenu mnuEmpleados;
    private javax.swing.JMenu mnuEmpresa;
    private javax.swing.JMenuItem mnuItemGestorEmpleado;
    private javax.swing.JMenuItem mnuItmAcerca;
    private javax.swing.JMenuItem mnuItmAltaEmpleado;
    private javax.swing.JMenuItem mnuItmCerrarSesion;
    private javax.swing.JMenuItem mnuItmConfigurarEmpresa;
    private javax.swing.JMenuItem mnuItmCuentaAdmin;
    private javax.swing.JMenuItem mnuItmIniciarSesion;
    private javax.swing.JMenuItem mnuItmSalir;
    private javax.swing.JMenuItem mnuItmeGestorAsistencia;
    private javax.swing.JPanel panelCam;
    private org.edisoncor.gui.panel.PanelRectTranslucidoComplete panelInicio;
    private org.edisoncor.gui.varios.TitleBar titleBar1;
    private org.edisoncor.gui.textField.TextFieldRectIcon txtLDni;
    // End of variables declaration//GEN-END:variables

}
