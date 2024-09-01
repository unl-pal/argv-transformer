package mtgdeckbuilder.frontend;

import mtgdeckbuilder.data.Field;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.data.Function;
import mtgdeckbuilder.frontend.topics.AddFilterTopic;
import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static mtgdeckbuilder.util.FrontEndTestingUtils.click;
import static mtgdeckbuilder.util.FrontEndTestingUtils.findComponentRecursively;
import static mtgdeckbuilder.util.FrontEndTestingUtils.pressEnterIn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NewFilterPanelTest {

    private AddFilterTopic addFilterTopic = mock(AddFilterTopic.class);

    private NewFilterPanel newFilterPanel = new NewFilterPanel(addFilterTopic);

}
