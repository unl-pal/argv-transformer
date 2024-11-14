package cyd.awesome.material;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import cyd.awesome.material.R;

/**
 * Created by daniellevass on 12/04/15.
 */
public class AwesomeButton extends LinearLayout {

    private DrawableLayout drawableLayout;
    private DrawableLayoutUtil drawableLayoutUtil;


    private Typeface font;

    private String iconLeft;
    private String textMiddle;
    private String iconMiddle;
    private String iconRight;

    private boolean useRounded;

    private int textSize;


    private TextView lblLeft;
    private TextView lblMiddle;
    private TextView lblRight;



    public enum AwesomeFont {

        FONT_AWESOME, MATERIAL_DESIGN, PIXEDEN_STROKE

    }

    /*
    public void setFontAwesomeIcon(FontCharacterMaps.FontAwesome icon) {
        this.setText(icon.toString());
        this.setTypeface(FontUtil.getFontAwesome(getContext()));
    }

    public void setMaterialDesignIcon(FontCharacterMaps.MaterialDesign icon) {
        this.setText(icon.toString());
        this.setTypeface(FontUtil.getMaterialDesignFont(getContext()));
    }

    public void setPixedenStrokeIcon(FontCharacterMaps.Pixeden icon) {
        this.setText(icon.toString());
        this.setTypeface(FontUtil.getPixedenFont(getContext()));
    }*/

}
