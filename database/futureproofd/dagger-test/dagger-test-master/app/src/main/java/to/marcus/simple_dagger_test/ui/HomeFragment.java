package to.marcus.simple_dagger_test.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import java.util.ArrayList;
import javax.inject.Inject;
import to.marcus.simple_dagger_test.BaseApplication;
import to.marcus.simple_dagger_test.BaseFragment;
import to.marcus.simple_dagger_test.R;
import to.marcus.simple_dagger_test.model.Photos.Photo;
import to.marcus.simple_dagger_test.modules.PresenterModule;
import to.marcus.simple_dagger_test.ui.adapter.PhotoAdapter;
import to.marcus.simple_dagger_test.ui.presenter.ImagePresenter;

/**
 * Created by marcus on 31/03/15!
 */
public class HomeFragment extends BaseFragment implements ImagePresenter.ImageView{
    private final String TAG = "HomeFragment";
    GridView mGridView;
    ArrayList<Photo> mImages;
    @Inject ImagePresenter presenter;

    //showloadingindicator()
        //progressIndicator.setVisibility(Visible)

    //shownoresultsfound
        //showerror

}
