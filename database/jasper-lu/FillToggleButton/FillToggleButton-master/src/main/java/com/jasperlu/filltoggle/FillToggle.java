package com.jasperlu.filltoggle;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ToggleButton;

/**
 * Created by Jasper on 3/31/2015.
 */
public class FillToggle extends ToggleButton {
    private int checkedFill;
    private int checkedBorder;
    private int uncheckedFill;
    private int uncheckedBorder;
    private int pressedFill;
    private int pressedBorder;
    private int onTextColor;
    private int offTextColor;

    private ToggleSelector backgroundSelector;
    private ToggleTextColor textColor;

    ShapeDrawable checked, unchecked, pressed;
}
