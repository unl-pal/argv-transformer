package is.arontibo.library;

import android.graphics.drawable.Animatable;
import android.os.Handler;

/**
 * Created by thibaultguegan on 21/03/15.
 */
public class AVDWrapper {

    private Handler mHandler;
    private Animatable mDrawable;
    private Callback mCallback;
    private Runnable mAnimationDoneRunnable = new Runnable() {

        @Override
        public void run() {
            if (mCallback != null) {
                mCallback.onAnimationDone();
            }
        }
    };

    public interface Callback {
    }

}
