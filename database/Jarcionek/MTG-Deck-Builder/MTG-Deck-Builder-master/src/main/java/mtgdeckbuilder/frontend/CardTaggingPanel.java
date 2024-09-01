package mtgdeckbuilder.frontend;

import mtgdeckbuilder.TestCode;
import mtgdeckbuilder.backend.TagsManager;
import mtgdeckbuilder.frontend.topics.TagTopic;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CardTaggingPanel extends JPanel implements TagTopic.Subscriber {

    private final CardsDisplayPanel cardsDisplayPanel;
    private final TagsManager tagsManager;
    private final TagTopic tagTopic;

    private final JComboBox<String> jComboBox;
    private final JButton tagButton;
    private final JButton untagButton;

}
