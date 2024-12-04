package is.arontibo.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by thibaultguegan on 15/03/15.
 */
public class ElasticDownloadView extends FrameLayout implements IntroView.EnterAnimationListener {

    private static final String LOG_TAG = ElasticDownloadView.class.getSimpleName();

    private IntroView mIntroView;
    private ProgressDownloadView mProgressDownloadView;
    private int mBackgroundColor;

}
