package carparksystem.views;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;

import carparksystem.controls.*;

/** 
 * This is a boundary class providing a graphical interface for
 * customer payment at the Exit.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/
public class PaymentUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private OutFrame outFrame;
	private JButton p10Btn, p20Btn, p50Btn,
		            f1Btn, f2Btn, confirmBtn;
	private JTextField timeTF, paymentTF, coinsTF;
	private double payment;
	private double coins;
}
