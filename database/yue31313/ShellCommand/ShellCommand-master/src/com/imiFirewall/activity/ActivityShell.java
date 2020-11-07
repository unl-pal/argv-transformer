package com.imiFirewall.activity;
// /system/bin/sh -c cmd arg0 arg1.....
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.imiFirewall.R;
import com.imiFirewall.imiApi;
import com.imiFirewall.R.id;
import com.imiFirewall.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ActivityShell extends Activity{
	 
	  Button btn_exe;	  
	  TextView tv_output;	 
	  private AutoCompleteTextView actinput; 
	  
	  public static final int WHITE=0xffffffff;
	  public static final int BLACK=0xff000000;
}