package is.arontibo.library;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import is.arontibo.library.VectorCompat.AnimatedVectorDrawable;

/**
 * Created by thibaultguegan on 15/03/15.
 */
public class IntroView extends ImageView {

    private static final String LOG_TAG = IntroView.class.getSimpleName();

    public interface EnterAnimationListener {
    }

    private EnterAnimationListener mListener;

}
