package cruise.umple.umpr.core.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Throwables;

/**
 * Utility methods for networking
 */
public abstract class Networks {
    /**
     * Supplies the content of a {@link URL} and uses HTTP caching to try to avoid redownloading the file as they tend
     * to be large. 
     * 
     * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
     * @since Mar 2, 2015
     */
    private static class URLSupplier implements Supplier<String> {
      
      private final Logger log = Logger.getLogger(URLSupplier.class.getName());
      
      private final URL url;
      
      private long timestamp = -1;
      private Optional<String> content = Optional.empty();
      
    }

}
