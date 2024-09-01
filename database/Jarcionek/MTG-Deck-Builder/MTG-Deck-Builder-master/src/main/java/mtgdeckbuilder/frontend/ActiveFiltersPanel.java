package mtgdeckbuilder.frontend;

import mtgdeckbuilder.TestCode;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.frontend.topics.AddFilterTopic;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ActiveFiltersPanel extends JPanel implements AddFilterTopic.Subscriber {

    private final JPanel innerPanel;
    private final List<Filter> filters;
    private final GridBagConstraints gridBagConstraints;

    @TestCode private int filterCount = 0;

}
