package mtgdeckbuilder.frontend;

import mtgdeckbuilder.backend.TagsManager;
import mtgdeckbuilder.frontend.topics.TagTopic;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static mtgdeckbuilder.util.FrontEndTestingUtils.click;
import static mtgdeckbuilder.util.FrontEndTestingUtils.findComponentRecursively;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardTaggingPanelTest {

    private static final String TAG_1 = "tag-1";
    private static final String TAG_2 = "tag-2";
    private static final String TAG_3 = "tag-3";
    private static final String TAG_4 = "tag-4";
    private static final List<String> AVAILABLE_TAGS = newArrayList(TAG_1, TAG_2, TAG_3);

    private CardsDisplayPanel cardsDisplayPanel = mock(CardsDisplayPanel.class);
    private TagsManager tagsManager = mock(TagsManager.class);
    private TagTopic tagTopic = mock(TagTopic.class);

    private CardTaggingPanel cardTaggingPanel;

}