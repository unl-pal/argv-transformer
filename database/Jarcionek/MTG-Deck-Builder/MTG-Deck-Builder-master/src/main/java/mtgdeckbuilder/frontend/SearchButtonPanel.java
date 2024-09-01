package mtgdeckbuilder.frontend;

import mtgdeckbuilder.TestCode;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.frontend.swingworkers.SearchProgressHarvest;
import mtgdeckbuilder.frontend.swingworkers.SearchSwingWorkerManager;
import mtgdeckbuilder.frontend.topics.SearchTopic;
import mtgdeckbuilder.frontend.topics.TagTopic;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchButtonPanel extends JPanel implements TagTopic.Subscriber {

    private final ActiveFiltersPanel activeFiltersPanel;
    private final CardsDisplayPanel cardsDisplayPanel;
    private final SearchSwingWorkerManager searchSwingWorkerManager;

    private final SearchTopic searchTopic;

    private final JButton searchButton;
    private final JLabel searchLabel;

    private class StatusUpdater implements SearchProgressHarvest {

        private int numberOfParts;

    }

}
