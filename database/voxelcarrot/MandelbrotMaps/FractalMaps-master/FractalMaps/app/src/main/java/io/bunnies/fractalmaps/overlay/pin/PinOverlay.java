package io.bunnies.fractalmaps.overlay.pin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import io.bunnies.fractalmaps.R;
import io.bunnies.fractalmaps.overlay.IFractalOverlay;

public class PinOverlay implements IFractalOverlay {
    private Context context;
    private Paint pinOuterPaint;
    private Paint pinInnerPaint;
    private float pinRadius;
    private float x;
    private float y;
    private boolean hilighted;

    private static final int OUTER_ALPHA = 150;
    private static final int INNER_ALPHA = 75;
}
