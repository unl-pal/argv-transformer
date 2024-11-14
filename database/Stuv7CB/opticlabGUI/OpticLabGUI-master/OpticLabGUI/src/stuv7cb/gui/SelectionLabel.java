package stuv7cb.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

class SelectionLabel extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7255951589130964243L;
	static MainFrame parent;
	protected final static String[] NAME={"Источник","Экран","Линза", "Зеркало", "Плоскопараллельная пластиика", "Лазер", "Призма", "Сферическое зеркало", "Толстая линза", "Эллептическое зеркало"};
	private int ID;
	class MyMouseListener implements MouseListener
	{
		
	}
}
