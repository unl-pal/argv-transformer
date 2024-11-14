package to.marcus.simple_dagger_test.event;

import android.util.Log;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import to.marcus.simple_dagger_test.model.ImageResponse;
import to.marcus.simple_dagger_test.network.ApiService;

/**
 * Created by marcus on 15/04/15
 * request handler for all API calls. Need to expand upon...
 */
public class ApiRequestHandler{

    private final Bus bus;
    private final ApiService apiService;
    private static final String TAG = "ApiRequestHandler";
}