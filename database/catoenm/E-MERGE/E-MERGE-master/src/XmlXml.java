
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mitchell
 */
public class GUI extends javax.swing.JFrame {

    //VARIABLE DECLARATION
    static File specSheet;
    static File fXmlFile;
    String modelNumber;
    String screwDiameter;
    boolean again = true;
		int modelColomnNumber = -1, colomnNumber = -1;
		ArrayList <String> specSheetValues = new ArrayList<String>();
		ArrayList <String> specSheetVariableNames = new ArrayList<String>();
		ArrayList <String> xmlValues = new ArrayList<String>();
		ArrayList <String> xmlVariableNames = new ArrayList<String>();
                ArrayList <String> mistakes = new ArrayList<String>();
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button StartButton;
    private javax.swing.JTextArea area;
    private java.awt.Button button1;
    private java.awt.Button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField machineDataPath;
    private javax.swing.JComboBox modelNumberText;
    private java.awt.Button proceedButton;
    private javax.swing.JComboBox screwDiameterText;
    private javax.swing.JTextField specSheetPath;
    // End of variables declaration//GEN-END:variables
}
