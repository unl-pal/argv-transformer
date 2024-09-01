package mtgdeckbuilder.frontend;

import mtgdeckbuilder.TestCode;
import mtgdeckbuilder.data.Field;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.data.Function;
import mtgdeckbuilder.frontend.topics.AddFilterTopic;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO Jarek: card name: Ajani's Pridemate - apostrophe is illegal character
//TODO Jarek: card name: Æther Adept - Æ is illegal character (use AE instead)
//TODO Jarek: card name: Error (Trial/Error) - cannot be used as file name due to slash
public class NewFilterPanel extends JPanel {

    private final AddFilterTopic addFilterTopic;

    private final JComboBox<Field> fieldComboBox;
    private final JComboBox<Function> functionComboBox;
    private final JTextField argumentTextField;
    private final JButton addButton;

}
