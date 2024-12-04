package org.sport.tracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.sport.tracker.utils.Record;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * RecordInfo activity, to show record data.
 * 
 * @author Waldemar Smirnow
 *
 */
public class RecordInfoUI extends Activity {
	
	/**
	 * Record ID.
	 */
	long recordId = 0;
	/**
	 * Record to show.
	 */
	Record record;
	/**
	 * Delete Record dialog id.
	 */
	static final int DELETE_DIALOG_ID = 0;
	/**
	 * Comment dialog id.
	 */
	static final int COMMENT_DIALOG_ID = 1;

}
