package carparksystem.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/** 
 * This is a boundary class providing an overall application GUI
 *  and scheduling a job for the event-dispatching thread
 *  
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class OutFrame extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	// The preferred size 
	private int PREFERRED_WIDTH = 1100;
	private int PREFERRED_HEIGHT = 550;
	
	private EntranceUI entranceUI;
	private ExitUI exitUI;
	
	private AdminUI   adminUI;
	private AccountUI accountUI;
	private ParkStatusUI parkStatusUI;
	private PaymentUI paymentUI;
	private PublicRecordsUI pulicRecordsUI;
	private StaffRecordsUI staffRecordsUI;
	private JPanel mainCtrlUI, rightUI;
	private JLabel statusField;
	
	private CardLayout cl1;
	private CardLayout cl2;
}
