package to.marcus.simple_dagger_test.modules;

import android.content.Context;
import com.squareup.otto.Bus;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import to.marcus.simple_dagger_test.BaseApplication;
import to.marcus.simple_dagger_test.network.ApiEndpoint;
import to.marcus.simple_dagger_test.network.ApiService;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */

@Module(
        injects = BaseApplication.class
)

public class ApplicationModule {

    private final String apiKey;
}
