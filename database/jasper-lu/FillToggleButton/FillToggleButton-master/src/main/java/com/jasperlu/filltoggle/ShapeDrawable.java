package com.jasperlu.filltoggle;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * Created by Jasper on 3/31/2015.
 */
public abstract class ShapeDrawable extends Drawable{
    public static final int RECT = 0;
    public static final int CIRCLE = 0;

    protected Paint mPaint;
    protected int fillColor;
    protected int borderColor;

    protected int mShape;
}
