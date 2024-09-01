package mtgdeckbuilder.frontend.topics;

import mtgdeckbuilder.data.Field;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.data.Function;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AddFilterTopicTest {

    private static final Filter FILTER = new Filter(Field.artist, Function.gte, "123");

    private AddFilterTopic topic = new AddFilterTopic();

}
