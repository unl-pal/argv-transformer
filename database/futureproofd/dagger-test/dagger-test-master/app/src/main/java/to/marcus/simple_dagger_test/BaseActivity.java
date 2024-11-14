package to.marcus.simple_dagger_test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import java.util.Arrays;
import java.util.List;
import to.marcus.simple_dagger_test.modules.ActivityModule;

/**
 * Created by marcus on 23/03/15
 */
public abstract class BaseActivity extends FragmentActivity {

    //inject the supplied object using the activity-specific graph
    /*
    public void inject(Object object){
        activityGraph.inject(object);
    }
    */
}
