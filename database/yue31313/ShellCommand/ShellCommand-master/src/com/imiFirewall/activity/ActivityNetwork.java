package com.imiFirewall.activity;

import java.util.Arrays;
import java.util.Comparator;
import java.lang.String;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;  //��ѡ���¼�
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.imiFirewall.R;
import com.imiFirewall.imiApi;
import com.imiFirewall.R.drawable;
import com.imiFirewall.R.id;
import com.imiFirewall.R.layout;
import com.imiFirewall.R.string;
import com.imiFirewall.imiApi.DroidApp;

public class ActivityNetwork extends Activity implements OnCheckedChangeListener,OnClickListener {
	
	
	private ListView listview;                     //�б��
	private ProgressDialog progress = null;        //������
	private ImageView iv;
	private TextView firewall_on;
	
    private static class ListEntry {
		private CheckBox box_wifi;
		private CheckBox box_3g;
		private TextView text;
	}
}