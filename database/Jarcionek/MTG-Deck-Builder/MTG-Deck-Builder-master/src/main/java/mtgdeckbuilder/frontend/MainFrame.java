package mtgdeckbuilder.frontend;

import mtgdeckbuilder.Config;
import mtgdeckbuilder.backend.CardImageDownloader;
import mtgdeckbuilder.backend.FilterToUrlConverter;
import mtgdeckbuilder.backend.ImageDownloader;
import mtgdeckbuilder.backend.JsonToCardsImageInfosConverter;
import mtgdeckbuilder.backend.TagFilesManager;
import mtgdeckbuilder.backend.TagsManager;
import mtgdeckbuilder.backend.UrlDownloader;
import mtgdeckbuilder.frontend.swingworkers.SearchSwingWorkerManager;
import mtgdeckbuilder.frontend.topics.AddFilterTopic;
import mtgdeckbuilder.frontend.topics.SearchTopic;
import mtgdeckbuilder.frontend.topics.TagTopic;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("FieldCanBeLocal")
public class MainFrame extends JFrame {

    private final AddFilterTopic addFilterTopic = new AddFilterTopic();
    private final TagTopic tagTopic = new TagTopic();
    private final SearchTopic searchTopic = new SearchTopic();

    private final NewFilterPanel newFilterPanel;
    private final ActiveFiltersPanel activeFiltersPanel;
    private final SearchButtonPanel searchButtonPanel;
    private final CardsDisplayPanel cardsDisplayPanel;
    private final TagViewer tagViewer;
    private final TagAddPanel tagAddPanel;
    private final CardTaggingPanel cardTaggingPanel;

}
