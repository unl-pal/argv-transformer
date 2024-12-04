package io.bunnies.fractalmaps.overlay.label;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import io.bunnies.fractalmaps.R;
import io.bunnies.fractalmaps.overlay.IFractalOverlay;

public class LabelOverlay implements IFractalOverlay {
    private float x;
    private float y;
    private String text;
    private Paint textPaint;

    private static final float TEXT_SIZE = 32.0f;
}
