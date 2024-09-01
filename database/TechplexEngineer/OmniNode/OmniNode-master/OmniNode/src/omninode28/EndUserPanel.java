/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omninode28;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
// is there a way to tell is a user is lookaing at a spesific tab?

public class EndUserPanel extends OmniPanel
{
    //private ArrayList<Node> Nodes;
    //private ArrayList<Connect> Connections;

    private int strlength;
    private JTextField abc;
    private JLabel label;
    private JButton sub;
    ArrayList<Node> nodelist;
    ArrayList<Connect> connectlist;
    DynamicListener DL;
    //-----------------------------------------------------------------
    //  Sets up this panel with two labels.
    //-----------------------------------------------------------------

    /**
     * listens for mouse events
     */
    class DynamicListener implements ActionListener, MouseListener, MouseMotionListener, WindowListener
    {

        JFrame omniFrame;
        ArrayList<Node> nodes;
        ArrayList<Connect> connections;
        JFileChooser fc;
        NodePanel NP;
        OmniPanel EP;
        Node WorkingNode;
        boolean onANode = false;
        Node end1, end2;
        int count = 0;
        boolean moving = false;
        int selectedCounter = 0;
    }
}



