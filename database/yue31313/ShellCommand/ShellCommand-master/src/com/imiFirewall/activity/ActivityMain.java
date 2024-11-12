package com.imiFirewall.activity;


import com.imiFirewall.R;
import com.imiFirewall.imiApi;
import com.imiFirewall.R.drawable;
import com.imiFirewall.R.id;
import com.imiFirewall.R.layout;
import com.imiFirewall.R.string;
import com.imiFirewall.activity.personal.ActivityPersonal;

import android.app.Activity;    //����android���������ǵ�android��ҪСд
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.view.Menu;     //����˵���
import android.view.MenuItem;

public class ActivityMain extends Activity {
    
	ImageButton btn_setting;
	ImageButton btn_network;
	ImageButton btn_call;
	ImageButton btn_sms;
	
	ImageButton btn_call_black;
	ImageButton btn_sms_black;
	
	ImageButton btn_call_record;
	ImageButton btn_private_space;
	
	ImageButton btn_help;
	
	 boolean firewall=true;
	 boolean callwall=false;
	 boolean smswall=false;
	
	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;
	
	
	private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
	private static final String EXTRA_SHORTCUT_DUPLICATE = "duplicate";
	
	static final int REQUEST_CODE = 1;
    
    
}