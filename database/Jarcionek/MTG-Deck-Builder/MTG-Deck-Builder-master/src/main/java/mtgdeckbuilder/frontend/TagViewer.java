package mtgdeckbuilder.frontend;

import mtgdeckbuilder.TestCode;
import mtgdeckbuilder.backend.TagsManager;
import mtgdeckbuilder.frontend.topics.SearchTopic;
import mtgdeckbuilder.frontend.topics.TagTopic;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;

public class TagViewer extends JPanel implements TagTopic.Subscriber, SearchTopic.Subscriber {

    private final TagsManager tagsManager;
    private final CardsDisplayPanel cardsDisplayPanel;
    private final TagTopic tagTopic;

    private final DefaultListModel<String> listModel;
    private final JList<String> list;

}
