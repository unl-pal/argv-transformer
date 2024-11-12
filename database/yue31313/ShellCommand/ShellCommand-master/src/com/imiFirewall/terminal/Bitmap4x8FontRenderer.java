package com.imiFirewall.terminal;

import com.imiFirewall.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;



class Bitmap4x8FontRenderer extends BaseTextRenderer {
  private final static int kCharacterWidth = 4;

  private final static int kCharacterHeight = 8;

  private Bitmap mFont;

  private int mCurrentForeColor;

  private int mCurrentBackColor;

  private float[] mColorMatrix;

  private Paint mPaint;

  private static final float BYTE_SCALE = 1.0f / 255.0f;

  private void setColorMatrix(int foreColor, int backColor) {
    if ((foreColor != mCurrentForeColor) || (backColor != mCurrentBackColor)
        || (mColorMatrix == null)) {
      mCurrentForeColor = foreColor;
      mCurrentBackColor = backColor;
      if (mColorMatrix == null) {
        mColorMatrix = new float[20];
        mColorMatrix[18] = 1.0f; // Just copy Alpha
      }
      for (int component = 0; component < 3; component++) {
        int rightShift = (2 - component) << 3;
        int fore = 0xff & (foreColor >> rightShift);
        int back = 0xff & (backColor >> rightShift);
        int delta = back - fore;
        mColorMatrix[component * 6] = delta * BYTE_SCALE;
        mColorMatrix[component * 5 + 4] = fore;
      }
      mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
    }
  }
}
