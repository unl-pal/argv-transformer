package mtgdeckbuilder.frontend;

import mtgdeckbuilder.backend.TagsManager;
import mtgdeckbuilder.frontend.topics.SearchTopic;
import mtgdeckbuilder.frontend.topics.TagTopic;
import org.junit.Test;

import javax.swing.JList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static mtgdeckbuilder.util.FrontEndTestingUtils.findComponentRecursively;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagViewerTest {

    private TagsManager tagsManager = mock(TagsManager.class);
    private CardsDisplayPanel cardsDisplayPanel = mock(CardsDisplayPanel.class);
    private SearchTopic searchTopic = mock(SearchTopic.class);
    private TagTopic tagTopic = mock(TagTopic.class);

    private TagViewer tagViewer;

}