package mtgdeckbuilder.frontend;

import com.shazam.shazamcrest.MatcherAssert;
import mtgdeckbuilder.data.Field;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.data.Function;
import mtgdeckbuilder.frontend.topics.AddFilterTopic;
import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JLabel;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static mtgdeckbuilder.util.FrontEndTestingUtils.click;
import static mtgdeckbuilder.util.FrontEndTestingUtils.containsComponentRecursively;
import static mtgdeckbuilder.util.FrontEndTestingUtils.findComponentRecursively;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ActiveFiltersPanelTest {

    private AddFilterTopic addFilterTopic = mock(AddFilterTopic.class);

    private ActiveFiltersPanel activeFiltersPanel = new ActiveFiltersPanel(addFilterTopic);

}
