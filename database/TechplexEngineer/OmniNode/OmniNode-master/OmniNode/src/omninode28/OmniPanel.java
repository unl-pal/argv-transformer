/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package omninode28;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 * Write a description of class OmniPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class OmniPanel extends JPanel
{

    protected ArrayList<Node> nodes;
    protected ArrayList<Connect> connections;

    //error messages

    //save abstract
    //public abstract void save(ArrayList<Node> nodelist, ArrayList<Connect> connectlist, String filename);
}

