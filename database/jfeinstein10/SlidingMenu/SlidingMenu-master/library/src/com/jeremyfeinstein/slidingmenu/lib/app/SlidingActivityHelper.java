package com.jeremyfeinstein.slidingmenu.lib.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.jeremyfeinstein.slidingmenu.lib.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SlidingActivityHelper {

	private Activity mActivity;

	private SlidingMenu mSlidingMenu;

	private View mViewAbove;

	private View mViewBehind;

	private boolean mBroadcasting = false;

	private boolean mOnPostCreateCalled = false;

	private boolean mEnableSlide = true;

}
