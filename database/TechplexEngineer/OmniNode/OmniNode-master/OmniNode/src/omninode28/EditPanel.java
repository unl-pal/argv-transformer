/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omninode28;

import java.awt.event.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author student
 */
public class EditPanel extends OmniPanel {

    //protected ArrayList<Node> Nodes;
    //protected ArrayList<Connect> Connections;
    DynamicListener DL;
}

/*
 * 
 */
class DynamicListener implements ActionListener, MouseListener, MouseMotionListener, WindowListener {

    JFrame omniFrame;
    ArrayList<Node> nodes;
    ArrayList<Connect> connections;
    JFileChooser fc;
    NodePanel NP;
    OmniPanel EP;
    Node WorkingNode;
	Point lastPoint;
    boolean onANode = false;
    Node end1, end2;
    int count = 0;
    boolean moving = false;
    int selectedCounter = 0;

    Node n1, n2;
}


