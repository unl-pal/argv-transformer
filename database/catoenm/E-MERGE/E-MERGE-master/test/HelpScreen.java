
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
public class ChangeXML extends javax.swing.JFrame {

    /**
     * Creates new form ChangeXML
     */
    static ArrayList<String> specSheetValues = new ArrayList<String>();
    static ArrayList<String> specSheetVariableNames = new ArrayList<String>();
    static ArrayList<String> xmlValues = new ArrayList<String>();
    static ArrayList<String> xmlVariableNames = new ArrayList<String>();
    static ArrayList<String> mistakes = new ArrayList<String>();
    static File fXmlFile;
    private static DefaultListModel model;
    static String specValue = "", xmlValue = "";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Comparison1;
    private javax.swing.JLabel Comparison2;
    private java.awt.Button button1;
    private java.awt.Button button2;
    private javax.swing.JLabel comparison1;
    private javax.swing.JLabel comparison2;
    private javax.swing.JList incorrectVariables;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
