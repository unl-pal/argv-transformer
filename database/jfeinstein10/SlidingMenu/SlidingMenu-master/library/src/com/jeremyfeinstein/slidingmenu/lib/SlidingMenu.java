package com.jeremyfeinstein.slidingmenu.lib;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jeremyfeinstein.slidingmenu.lib.CustomViewAbove.OnPageChangeListener;

public class SlidingMenu extends RelativeLayout {

	private static final String TAG = SlidingMenu.class.getSimpleName();

	public static final int SLIDING_WINDOW = 0;
	public static final int SLIDING_CONTENT = 1;
	private boolean mActionbarOverlay = false;

	/** Constant value for use with setTouchModeAbove(). Allows the SlidingMenu to be opened with a swipe
	 * gesture on the screen's margin
	 */
	public static final int TOUCHMODE_MARGIN = 0;

	/** Constant value for use with setTouchModeAbove(). Allows the SlidingMenu to be opened with a swipe
	 * gesture anywhere on the screen
	 */
	public static final int TOUCHMODE_FULLSCREEN = 1;

	/** Constant value for use with setTouchModeAbove(). Denies the SlidingMenu to be opened with a swipe
	 * gesture
	 */
	public static final int TOUCHMODE_NONE = 2;

	/** Constant value for use with setMode(). Puts the menu to the left of the content.
	 */
	public static final int LEFT = 0;

	/** Constant value for use with setMode(). Puts the menu to the right of the content.
	 */
	public static final int RIGHT = 1;

	/** Constant value for use with setMode(). Puts menus to the left and right of the content.
	 */
	public static final int LEFT_RIGHT = 2;

	private CustomViewAbove mViewAbove;

	private CustomViewBehind mViewBehind;

	private OnOpenListener mOpenListener;
	
	private OnOpenListener mSecondaryOpenListner;

	private OnCloseListener mCloseListener;

	/**
	 * The listener interface for receiving onOpen events.
	 * The class that is interested in processing a onOpen
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnOpenListener<code> method. When
	 * the onOpen event occurs, that object's appropriate
	 * method is invoked
	 */
	public interface OnOpenListener {
	}

	/**
	 * The listener interface for receiving onOpened events.
	 * The class that is interested in processing a onOpened
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnOpenedListener<code> method. When
	 * the onOpened event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnOpenedEvent
	 */
	public interface OnOpenedListener {
	}

	/**
	 * The listener interface for receiving onClose events.
	 * The class that is interested in processing a onClose
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnCloseListener<code> method. When
	 * the onClose event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnCloseEvent
	 */
	public interface OnCloseListener {
	}

	/**
	 * The listener interface for receiving onClosed events.
	 * The class that is interested in processing a onClosed
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnClosedListener<code> method. When
	 * the onClosed event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnClosedEvent
	 */
	public interface OnClosedListener {
	}

	/**
	 * The Interface CanvasTransformer.
	 */
	public interface CanvasTransformer {
	}

	public static class SavedState extends BaseSavedState {

		private final int mItem;

		public static final Parcelable.Creator<SavedState> CREATOR =
				new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};

	}

}
