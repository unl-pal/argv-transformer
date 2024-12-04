package is.arontibo.library.VectorCompat;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ValueAnimator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import java.io.IOException;
import java.util.ArrayList;

import is.arontibo.library.R;

public class AnimatedVectorDrawable extends DrawableCompat implements Animatable, Tintable {

    private static final String LOG_TAG = AnimatedVectorDrawable.class.getSimpleName();

    private static final String ANIMATED_VECTOR = "animated-vector";
    private static final String TARGET = "target";

    private static final boolean DBG_ANIMATION_VECTOR_DRAWABLE = false;

    private AnimatedVectorDrawableState mAnimatedVectorState;

    private boolean mMutated;

    private static class AnimatedVectorDrawableState extends Drawable.ConstantState {
        int mChangingConfigurations;
        VectorDrawable mVectorDrawable;
        ArrayList<Animator> mAnimators;
        ArrayMap<Animator, String> mTargetNameMap;
    }

}
