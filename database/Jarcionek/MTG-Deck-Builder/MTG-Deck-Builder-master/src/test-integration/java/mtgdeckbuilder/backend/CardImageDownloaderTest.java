package mtgdeckbuilder.backend;

import mtgdeckbuilder.data.CardImageInfo;
import mtgdeckbuilder.data.Url;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.File;
import java.io.IOException;
import java.util.AbstractSet;
import java.util.Iterator;

import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CardImageDownloaderTest {

    private static final String IMAGE_NAME_ONE = "one";
    private static final String IMAGE_NAME_TWO = "two";
    private static final String IMAGE_NAME_THREE = "three";
    private static final File CARDS_DIRECTORY = new File(CardImageDownloaderTest.class + "-dir");
    private static final File LOW_RES_IMAGES_DIRECTORY = new File(CARDS_DIRECTORY, "low");
    private static final File HIGH_RES_IMAGES_DIRECTORY = new File(CARDS_DIRECTORY, "high");
    private static final File LOW_RES_FILE_ONE = new File(LOW_RES_IMAGES_DIRECTORY, IMAGE_NAME_ONE + ".jpg");
    private static final File LOW_RES_FILE_TWO = new File(LOW_RES_IMAGES_DIRECTORY, IMAGE_NAME_TWO + ".jpg");
    private static final File LOW_RES_FILE_THREE = new File(LOW_RES_IMAGES_DIRECTORY, IMAGE_NAME_THREE + ".jpg");
    private static final File HIGH_RES_FILE_ONE = new File(HIGH_RES_IMAGES_DIRECTORY, IMAGE_NAME_ONE + ".jpg");
    private static final File HIGH_RES_FILE_TWO = new File(HIGH_RES_IMAGES_DIRECTORY, IMAGE_NAME_TWO + ".jpg");
    private static final File HIGH_RES_FILE_THREE = new File(HIGH_RES_IMAGES_DIRECTORY, IMAGE_NAME_THREE + ".jpg");

    private ImageDownloader imageDownloader = mock(ImageDownloader.class);
    private CardImageDownloadProgressHarvest progressHarvest = mock(CardImageDownloadProgressHarvest.class);

    private CardImageDownloader cardImageDownloader = new CardImageDownloader(CARDS_DIRECTORY, imageDownloader);

    private static class InfiniteSet extends AbstractSet<CardImageInfo> {

        private final CardImageInfo element = new CardImageInfo(0, "element");

    }

}
