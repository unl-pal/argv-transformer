package is.arontibo.library.VectorCompat;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

public abstract class DrawableCompat extends Drawable {
    int mLayoutDirection;

    public static abstract class ConstantStateCompat extends ConstantState {
    }
}
