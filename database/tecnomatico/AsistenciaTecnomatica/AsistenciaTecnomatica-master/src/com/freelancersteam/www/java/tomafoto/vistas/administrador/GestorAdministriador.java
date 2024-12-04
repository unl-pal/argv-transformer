/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GestorAdministriador.java
 *
 * Created on 05-may-2013, 11:15:35
 */
package com.freelancersteam.www.java.tomafoto.vistas.administrador;

import com.freelancersteam.www.java.tomafoto.dominio.Empleado;
import com.freelancersteam.www.java.tomafoto.dominio.dao.imp.EmpleadoDaoImp;
import com.freelancersteam.www.java.tomafoto.util.Constantes;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alumno
 */
public class GestorAdministriador extends javax.swing.JDialog {
    int legajo;
    private List<Empleado> listaEmpleado;
    DefaultTableModel modelo;
    java.awt.Frame parent;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIpod btnCancelarOperacion;
    private org.edisoncor.gui.button.ButtonIpod btnModificar;
    private org.edisoncor.gui.button.ButtonIpod btnNuevo;
    private javax.swing.JScrollPane jScrollPane2;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTable tblEmpleado;
    // End of variables declaration//GEN-END:variables
}
