package stuv7cb.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

abstract class SetPanel extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1557450034789001791L;
	protected JTextField xcord = new JTextField("x");
	protected JTextField ycord = new JTextField("y");
	protected JTextField angle = new JTextField("Угол");
	protected MainFrame frame;
}
