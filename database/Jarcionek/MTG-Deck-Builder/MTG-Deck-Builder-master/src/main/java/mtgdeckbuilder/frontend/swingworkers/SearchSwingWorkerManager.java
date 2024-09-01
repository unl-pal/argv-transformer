package mtgdeckbuilder.frontend.swingworkers;

import com.google.common.collect.ImmutableList;
import mtgdeckbuilder.backend.CardImageDownloadProgressHarvest;
import mtgdeckbuilder.backend.CardImageDownloader;
import mtgdeckbuilder.backend.FilterToUrlConverter;
import mtgdeckbuilder.backend.JsonToCardsImageInfosConverter;
import mtgdeckbuilder.backend.UrlDownloader;
import mtgdeckbuilder.data.CardImageInfo;
import mtgdeckbuilder.data.Filter;
import mtgdeckbuilder.data.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingWorker;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;

public class SearchSwingWorkerManager {

    private final Logger log = LoggerFactory.getLogger(SearchSwingWorkerManager.class);

    private final FilterToUrlConverter filterToUrlConverter;
    private final UrlDownloader urlDownloader;
    private final JsonToCardsImageInfosConverter jsonToCardsImageInfosConverter;
    private final CardImageDownloader cardImageDownloader;

    private SearchSwingWorker swingWorker;

    private class SearchSwingWorker extends SwingWorker<List<String>, ProgressUpdate> {

        private final List<Filter> filters;
        private final SearchProgressHarvest searchProgressHarvest;
        private final CardImageDownloadProgressHarvest cardImageDownloadProgressHarvest;

        private List<String> foundCards;

    }

}
