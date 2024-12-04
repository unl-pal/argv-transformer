package com.bashizip.andromed;

import java.util.ArrayList;
import java.util.List;

import com.bashizip.andromed.data.Quote;
import com.bashizip.andromed.datahelper.DBTool;
import com.db4o.ObjectSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.TextView;

public class QuotesActivity extends Activity {

	private DBTool dbcon;
	private TextView tv_quote, tv_from, tv_current;
	//private Button btn_next, btn_prev;
	private static int QUOTE_ID = 0;

	private List<Quote> quotes;

	class PromptListener implements
			android.content.DialogInterface.OnClickListener {
		// local variable to return the prompt reply value
		private int promptReply = 1;
		// Keep a variable for the view to retrieve the prompt value
		View promptDialogView = null;
	}

	class MyGestureListner extends SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	}

}
