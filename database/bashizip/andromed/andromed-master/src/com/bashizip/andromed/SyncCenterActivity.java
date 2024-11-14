package com.bashizip.andromed;

import java.util.List;

import com.bashizip.andromed.data.*;
import com.bashizip.andromed.gae.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bashizip.andromed.data.Patient;
import com.bashizip.andromed.datahelper.DBTool;
import com.db4o.query.Predicate;

public class SyncCenterActivity extends Activity implements OnClickListener {

	private Button btn_m2c;
	private ProgressDialog pDial;
	private final PostControler pCtrl = new PostControler();
	TextView tv_sync_count;

}
