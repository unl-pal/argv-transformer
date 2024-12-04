/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancersteam.www.java.tomafoto.vistas.asistencia;

import com.freelancersteam.www.java.tomafoto.vistas.main.Camara;
import com.freelancersteam.www.java.tomafoto.vistas.empleado.GestorEmpleado;
import com.freelancersteam.www.java.tomafoto.util.TablaUtil;

import com.freelancersteam.www.java.tomafoto.dominio.Asistencia;
import com.freelancersteam.www.java.tomafoto.dominio.Empleado;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.AsistenciaDaoImp;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.EmpleadoDaoImp;
import com.freelancersteam.www.java.tomafoto.util.Constantes;
import com.freelancersteam.www.java.tomafoto.util.FechaUtil;
import com.freelancersteam.www.java.tomafoto.util.ReporteAsitenciaJRDataSource;
import java.awt.Color;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Leo
 */
public class GestorAsistencia extends javax.swing.JDialog {
//    Set<Asistencia> conjunto;
    DefaultTableModel modelo;
    private List<Asistencia> listaAsistencia;
    java.awt.Frame parent;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIpod btnBuscar;
    private org.edisoncor.gui.button.ButtonIcon btnBusquedaPersonal;
    private org.edisoncor.gui.button.ButtonIpod btnImprimir;
    private org.edisoncor.gui.button.ButtonIpod btnSalir;
    private org.edisoncor.gui.button.ButtonIpod btnVerEditar;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.edisoncor.gui.comboBox.ComboBoxRound cmbBusqueda;
    private com.toedter.calendar.JDateChooser fechaFin;
    private com.toedter.calendar.JDateChooser fechaInicio;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.PanelShadow panelShadow1;
    private javax.swing.JRadioButton rdbFecha;
    private javax.swing.JRadioButton rdbHoy;
    private javax.swing.JRadioButton rdbMes;
    private javax.swing.JTable tblAsistencia;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtBusqueda;
}
