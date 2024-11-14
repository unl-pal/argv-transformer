package to.marcus.simple_dagger_test;

import android.app.Application;
import android.content.Context;
import com.squareup.otto.Bus;
import javax.inject.Inject;
import dagger.ObjectGraph;
import to.marcus.simple_dagger_test.event.ApiRequestHandler;
import to.marcus.simple_dagger_test.modules.Modules;
import to.marcus.simple_dagger_test.network.ApiService;

/**
 * Created by marcus on 23/03/15
 */

public class BaseApplication extends Application {
    private ObjectGraph applicationGraph;
    @Inject Bus bus;
    @Inject ApiService apiService;
}
