package mtgdeckbuilder.backend;

import mtgdeckbuilder.data.Url;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static mtgdeckbuilder.Utils.assertImagesEqual;
import static mtgdeckbuilder.Utils.loadResourceImage;
import static org.junit.Assert.assertEquals;

public class ImageDownloaderTest {

    private static final Url URL = new Url("http://api.mtgdb.info/content/hi_res_card_images/386623.jpg");
    private static final File FILE = new File(ImageDownloaderTest.class.getSimpleName() + "-file.jpg");
    private static final File FILE_IN_NON_EXISTING_DIRECTORY = new File(ImageDownloaderTest.class.getSimpleName() + "-dir",
                                                                        ImageDownloaderTest.class.getSimpleName() + "-file.jpg");

    private static final BufferedImage EXPECTED_IMAGE = loadResourceImage("ImageDownloaderTest-expectedFile.jpg");

    private ImageDownloader imageDownloader = new ImageDownloader();

}
