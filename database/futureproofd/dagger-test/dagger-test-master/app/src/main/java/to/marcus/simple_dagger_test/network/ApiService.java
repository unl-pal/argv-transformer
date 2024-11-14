package to.marcus.simple_dagger_test.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import to.marcus.simple_dagger_test.model.ImageResponse;

/**
 * Created by marcus on 27/04/15.
 */
public interface ApiService {

    String method = "flickr.photos.getRecent";
    static final String API_KEY = "4b37ee018d17b1aa7cd793ede2ea7ee7";
}
