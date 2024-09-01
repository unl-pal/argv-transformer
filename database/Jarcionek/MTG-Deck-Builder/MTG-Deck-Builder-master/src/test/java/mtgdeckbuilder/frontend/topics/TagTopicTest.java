package mtgdeckbuilder.frontend.topics;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TagTopicTest {

    private TagTopic.Subscriber subscriberOne = mock(TagTopic.Subscriber.class);
    private TagTopic.Subscriber subscriberTwo = mock(TagTopic.Subscriber.class);

    private TagTopic topic = new TagTopic();

}