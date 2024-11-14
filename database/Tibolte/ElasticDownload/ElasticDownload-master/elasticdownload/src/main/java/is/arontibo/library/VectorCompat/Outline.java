package is.arontibo.library.VectorCompat;

import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Defines a simple shape, used for bounding graphical regions.
 * <p>
 * Can be computed for a View, or computed by a Drawable, to drive the shape of
 * shadows cast by a View, or to clip the contents of the View.
 *
 * @see android.view.ViewOutlineProvider
 * @see android.view.View#setOutlineProvider(android.view.ViewOutlineProvider)
 */
public final class Outline {

    public Path mPath;
    public Rect mRect;
    public float mRadius;
    public float mAlpha;

}
