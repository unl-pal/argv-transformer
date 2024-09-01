package mtgdeckbuilder.frontend.swingworkers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import mtgdeckbuilder.backend.CardImageDownloadProgressHarvest;
import mtgdeckbuilder.backend.CardImageDownloader;
import mtgdeckbuilder.backend.FilterToUrlConverter;
import mtgdeckbuilder.backend.JsonToCardsImageInfosConverter;
import mtgdeckbuilder.backend.UrlDownloader;
import mtgdeckbuilder.data.CardImageInfo;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.data.Url;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import javax.swing.SwingUtilities;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static mtgdeckbuilder.data.Field.color;
import static mtgdeckbuilder.data.Field.manacost;
import static mtgdeckbuilder.data.Function.lt;
import static mtgdeckbuilder.data.Function.m;
import static org.apache.log4j.Logger.getRootLogger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class SearchSwingWorkerManagerTest {

    private static final Appender NO_OP_APPENDER = new AppenderSkeleton() {
        @Override
        protected void append(LoggingEvent loggingEvent) {}

        @Override
        public void close() {}

        @Override
        public boolean requiresLayout() {
            return false;
        }
    };

    private static final List<Filter> FILTERS = ImmutableList.of(new Filter(color, m, "blue"), new Filter(manacost, lt, "3"));
    private static final String URL = "blah.com";
    private static final String JSON = "this is cards data in json format";
    private static final ImmutableSet<CardImageInfo> CARD_IMAGE_INFOS = ImmutableSet.of(new CardImageInfo(1, "name1"), new CardImageInfo(2, "name2"));

    @Rule public final Timeout timeout = new Timeout(1000);

    private final Object lock = new Object();

    private FilterToUrlConverter filterToUrlConverter = mock(FilterToUrlConverter.class);
    private UrlDownloader urlDownloader = mock(UrlDownloader.class);
    private JsonToCardsImageInfosConverter jsonToCardsImageInfosConverter = mock(JsonToCardsImageInfosConverter.class);
    private CardImageDownloader cardImageDownloader = mock(CardImageDownloader.class);
    private SearchProgressHarvest searchProgressHarvest = mock(SearchProgressHarvest.class);

    private SearchSwingWorkerManager searchSwingWorkerManager = new SearchSwingWorkerManager(filterToUrlConverter, urlDownloader, jsonToCardsImageInfosConverter, cardImageDownloader);

    private class SearchProgressHarvestStub implements SearchProgressHarvest {

        private boolean startedWasOnEventDispatchThread = true;
        private boolean partDoneWasOnEventDispatchThread = true;
        private boolean finishedWasOnEventDispatchThread = true;
        private boolean errorWasOnEventDispatchThread = true;

    }

}