package com.imiFirewall.activity;

import java.util.ArrayList;
import java.util.List;

import com.imiFirewall.R;
import com.imiFirewall.TabManagerAdapter;
import com.imiFirewall.imiSql;
import com.imiFirewall.Interface.PersonSelectListener;
import com.imiFirewall.common.Commons;
import com.imiFirewall.util.imiPersonSelectDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;



public class ActivitySpamList extends TabActivity 
       implements TabHost.OnTabChangeListener,AdapterView.OnItemClickListener, PersonSelectListener
{
	private TabHost  tabHost;	
	private static final String[] TAB;
	private imiSql mydb=null;
	private ListView listView = null;
	private ArrayList mDataArray;
	private TabManagerAdapter mAdapter=null;
	
	private EditText mText1;
	private EditText mText2;
	
	final CharSequence [] items = { "Red" , "Green" , "Blue" };
	
	static{
		String[] tabString = new String[2];
		tabString[0]       ="BLACKLIST";
		tabString[1]       ="WHITELIST";
		TAB=tabString;
	}
}