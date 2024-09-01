/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package omninode28;




/**
 * The purpose of a node it to represent one box in the web.
 *
 * @author (BlakeB)
 * @version (v1.0)
 */
//@ todo issue with guessing can guess hidden nodes

import java.util.ArrayList;
import java.awt.*;

public class Node
{
    private Point loc;
    private String name;  // name of the node
    private int boxSize;  // calculated by name length
    private Point center; // approx center
    private String state = "hidden";
    private boolean selected = false;
    private ArrayList<Connect> myConnections; // list of all the connections that connect to me
    private boolean debug = false;
    int height = 25;




}
