package mtgdeckbuilder.frontend;

import mtgdeckbuilder.backend.TagsManager;
import mtgdeckbuilder.frontend.topics.TagTopic;
import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JTextField;

import static mtgdeckbuilder.util.FrontEndTestingUtils.click;
import static mtgdeckbuilder.util.FrontEndTestingUtils.findComponentRecursively;
import static mtgdeckbuilder.util.FrontEndTestingUtils.pressEnterIn;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TagAddPanelTest {

    private static final String TAG_NAME_ONE = "this-is-a-new-tag";
    private static final String TAG_NAME_TWO = "tagName";
    private static final String TAG_NAME_THREE = "justAnotherTag";
    private static final String TAG_NAME_EMPTY_STRING = "";

    private TagsManager tagsManager = mock(TagsManager.class);
    private TagTopic tagTopic = mock(TagTopic.class);

    private TagAddPanel tagAddPanel = new TagAddPanel(tagsManager, tagTopic);

}
