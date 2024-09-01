package com.jacob.circle.disc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Package : com.jacob.circle.disc
 * Author : jacob
 * Date : 15-4-1
 * Description : 这个类是用来xxx
 */
public class CircleIndexView extends ViewGroup {

    private Context mContext;

    /**
     * 选择圆盘的背景
     */
    private Bitmap mBitmapBg;

    /**
     * 当前是否显示出了圆盘
     */
    private boolean isShowCircle = false;

    /**
     * 26个字母放在一个圆盘中，这是没一个字母对应圆盘的角度
     */
    private static float sPerAngle = 360.0f / 26;

    /**
     * 旋转的角度
     */
    private float mAngle = 0;

    /**
     * 开始的角度
     */
    private float mStartAngle = 0;

    /**
     * 屏幕中心点的坐标
     */
    private float mCenterXY;

    /**
     * 字母数组，用于判断外部传入的字母对应的index 和角度
     */
    private String[] mLetters = new String[]{
            "a", "b", "c",
            "d", "e", "f",
            "g", "h", "i",
            "j", "k", "l",
            "m", "n", "o",
            "p", "q", "r",
            "s", "t", "u",
            "v", "w", "x",
            "y", "z"};


    /**
     * 事件回调的接口
     */
    private OnIndexChangeListener mIndexListener;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            View view = getChildAt(0);
            int childW = view.getMeasuredWidth();
            int childH = view.getMeasuredHeight();

            int left = getWidth() / 2 - childW / 2;
            int top = getHeight() / 2 - childH / 2;
            view.layout(left, top, left + childW, top + childH);
        }
    }


    /**
     * 记录上一次的位置
     */
    private float mLastX;
    private float mLastY;

    /**
     * 字符旋转更改后的事件回调接口
     */
    public interface OnIndexChangeListener {
    }
}
