package com.yq.circlepress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * @author Yangqing
 *
 */
public class RoundProgressBar extends View {
	/**
	 * 画笔对象的引用
	 */
	private final Paint paint;

	/**
	 * 圆环的颜色
	 */
	private int roundColor;

	/**
	 * 圆环进度的颜色
	 */
	private int roundProgressColor;

	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int textColor;

	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSize;

	/**
	 * 圆环的宽度
	 */
	private float roundWidth;

	/**
	 * 圆环进度条的宽度
	 */
	private final float roundProgressWidth;

	/**
	 * 最大进度
	 */
	private long max;

	/**
	 * 当前进度
	 */
	private long progress;
	/**
	 * 是否显示中间的进度
	 */
	private final boolean textIsDisplayable;

	/**
	 * 进度的风格，实心或者空心
	 */
	private final int style;

	/***结束时间*/
	private long endTime;

	public static final int STROKE = 0;
	public static final int FILL = 1;

	private boolean mTickerStopped;
	private Handler mHandler;
	private Runnable mTicker;
	public static long distanceTime;

	private long currentTime;
	private boolean first = true;

}
