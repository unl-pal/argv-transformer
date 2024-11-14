package is.arontibo.library;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by thibaultguegan on 15/02/15.
 */
public class ProgressDownloadView extends View {

    private static final String LOG_TAG = ProgressDownloadView.class.getSimpleName();

    public static final long ANIMATION_DURATION_BASE = 1250;

    private int mWidth, mHeight, bubbleAnchorX, bubbleAnchorY, mBubbleWidth, mBubbleHeight, mPadding;
    private Path mPathBlack, mPathWhite, mPathBubble;
    private Paint mPaintBlack, mPaintWhite, mPaintBubble, mPaintText;
    private float mDensity = getResources().getDisplayMetrics().density;
    private float mProgress = 0, mTarget = 0, mSpeedAngle = 0, mBubbleAngle = 0, mFailAngle = 0, mFlipFactor;
    private State mState = State.STATE_WORKING;

    private enum State {
        STATE_WORKING,
        STATE_FAILED,
        STATE_SUCCESS
    }

    /**
     * MARK: Update drawings
     */

    private void makePathBlack() {

        if (mPathBlack == null) {
            mPathBlack = new Path();
        }

        Path p = new Path();
        p.moveTo(Math.max(getPaddingLeft(), mProgress * mWidth / 100), mHeight / 2 + calculateDeltaY());
        p.lineTo(mWidth, mHeight / 2);

        mPathBlack.set(p);
    }

    private void makePathWhite() {

        if (mPathWhite == null) {
            mPathWhite = new Path();
        }

        Path p = new Path();
        p.moveTo(getPaddingLeft(), mHeight / 2);
        p.lineTo(Math.max(getPaddingLeft(), mProgress * mWidth / 100), mHeight / 2 + calculateDeltaY());

        mPathWhite.set(p);
    }

    private void makePathBubble() {

        if (mPathBubble == null) {
            mPathBubble = new Path();
        }

        int width = mBubbleWidth;
        int height = mBubbleHeight;
        int arrowWidth = width / 3;

        //Rect r = new Rect(Math.max(getPaddingLeft()-width/2-arrowWidth/4, mProgress*mWidth/100-width/2-arrowWidth/4), mHeight/2-height + calculatedeltaY(), Math.max(getPaddingLeft()+width/2-arrowWidth/4, mProgress*mWidth/100+width/2-arrowWidth/4), mHeight/2+height-height + calculatedeltaY());
        Rect r = new Rect((int) (Math.max(getPaddingLeft() - width / 2, mProgress * mWidth / 100 - width / 2)), (int) (mHeight / 2 - height + calculateDeltaY()), (int) (Math.max(getPaddingLeft() + width / 2, mProgress * mWidth / 100 + width / 2)), (int) (mHeight / 2 + height - height + calculateDeltaY()));
        int arrowHeight = (int) (arrowWidth / 1.5f);
        int radius = 8;

        Path path = new Path();

        // Down arrow
        path.moveTo(r.left + r.width() / 2 - arrowWidth / 2, r.top + r.height() - arrowHeight);
        bubbleAnchorX = r.left + r.width() / 2;
        bubbleAnchorY = r.top + r.height();
        path.lineTo(bubbleAnchorX, bubbleAnchorY);
        path.lineTo(r.left + r.width() / 2 + arrowWidth / 2, r.top + r.height() - arrowHeight);

        // Go to bottom-right
        path.lineTo(r.left + r.width() - radius, r.top + r.height() - arrowHeight);

        // Bottom-right arc
        path.arcTo(new RectF(r.left + r.width() - 2 * radius, r.top + r.height() - arrowHeight - 2 * radius, r.left + r.width(), r.top + r.height() - arrowHeight), 90, -90);

        // Go to upper-right
        path.lineTo(r.left + r.width(), r.top + arrowHeight);

        // Upper-right arc
        path.arcTo(new RectF(r.left + r.width() - 2 * radius, r.top, r.right, r.top + 2 * radius), 0, -90);

        // Go to upper-left
        path.lineTo(r.left + radius, r.top);

        // Upper-left arc
        path.arcTo(new RectF(r.left, r.top, r.left + 2 * radius, r.top + 2 * radius), 270, -90);

        // Go to bottom-left
        path.lineTo(r.left, r.top + r.height() - arrowHeight - radius);

        // Bottom-left arc
        path.arcTo(new RectF(r.left, r.top + r.height() - arrowHeight - 2 * radius, r.left + 2 * radius, r.top + r.height() - arrowHeight), 180, -90);

        path.close();

        mPathBubble.set(path);
    }
}
