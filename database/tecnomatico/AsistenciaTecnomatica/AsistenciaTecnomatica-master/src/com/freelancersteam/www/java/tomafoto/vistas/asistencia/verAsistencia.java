/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancersteam.www.java.tomafoto.vistas.asistencia;

import com.freelancersteam.www.java.tomafoto.dominio.Asistencia;
import com.freelancersteam.www.java.tomafoto.dominio.Empleado;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.AsistenciaDaoImp;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.EmpleadoDaoImp;
import com.freelancersteam.www.java.tomafoto.estudiandojmf.mensajero;
import com.freelancersteam.www.java.tomafoto.util.Constantes;
import com.freelancersteam.www.java.tomafoto.util.FechaUtil;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Leo
 */
public class verAsistencia extends javax.swing.JDialog {

    private int idAsistencia;
    private int legajo;
    Asistencia a;  
    Empleado e;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIpod btnCancelar;
    private javax.swing.ButtonGroup btnGroupCorrecto;
    private org.edisoncor.gui.button.ButtonIpod btnGuardar;
    private org.edisoncor.gui.button.ButtonIpod btnReporte;
    private javax.swing.JCheckBox chkEditar;
    private org.edisoncor.gui.comboBox.ComboBoxRound cmbEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelMetric labelMetric2;
    private org.edisoncor.gui.label.LabelMetric labelMetric3;
    private org.edisoncor.gui.label.LabelMetric labelMetric4;
    private org.edisoncor.gui.label.LabelMetric labelMetric5;
    private org.edisoncor.gui.label.LabelMetric labelMetric6;
    private javax.swing.JLabel lblFotoAlta;
    private javax.swing.JLabel lblFotoAsistencia;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JRadioButton rbtnNo;
    private javax.swing.JRadioButton rbtnSi;
    private org.edisoncor.gui.textField.TextFieldRoundIcon txtComentario;
    private javax.swing.JLabel txtEmpleado;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JLabel txtHora;
    private javax.swing.JLabel txtLegajo;
    // End of variables declaration//GEN-END:variables
}
