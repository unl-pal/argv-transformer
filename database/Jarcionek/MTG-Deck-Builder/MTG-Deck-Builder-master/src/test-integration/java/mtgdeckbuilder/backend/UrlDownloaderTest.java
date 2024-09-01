package mtgdeckbuilder.backend;

import mtgdeckbuilder.data.Url;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * http://api.mtgdb.info/
 */
public class UrlDownloaderTest {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private UrlDownloader urlDownloader = new UrlDownloader();

}
