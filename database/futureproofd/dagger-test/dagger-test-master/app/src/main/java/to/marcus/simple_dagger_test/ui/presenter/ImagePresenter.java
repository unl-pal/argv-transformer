package to.marcus.simple_dagger_test.ui.presenter;

import android.util.Log;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import to.marcus.simple_dagger_test.event.ImagesReceivedEvent;
import to.marcus.simple_dagger_test.event.ImagesRequestedEvent;
import to.marcus.simple_dagger_test.model.Photos.Photo;

/**
 * Created by marcus on 15/04/15
 */

public class ImagePresenter{

    //define a View interface
    public interface ImageView{
    }

    private final String TAG = "ImagePresenter";
    private final Bus bus;
    private final ImageView view;

}
