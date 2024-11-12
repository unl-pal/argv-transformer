package com.imiFirewall.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.imiFirewall.PersonSelectAdapter;
import com.imiFirewall.R;
import com.imiFirewall.TabManagerAdapter;
import com.imiFirewall.Interface.ButtonGroupListener;
import com.imiFirewall.Interface.PersonSelectListener;
import com.imiFirewall.common.Commons;
import com.imiFirewall.common.Commons.PersonTypeData;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;




public class imiPersonSelectDialog extends Dialog
       implements AdapterView.OnItemClickListener ,ButtonGroupListener
{
	
	
	private ListView mListView;
	private ButtonGroup mBtnGroup;
	private PersonSelectListener mListener;
	private PersonSelectAdapter mSelectorAdapter;
	private ArrayList<Commons.PersonTypeData> mDataArray;
	private ArrayList<CheckBox> mCbArray;
	private SelectFromType mType;
	final int[] mNameArray;

	
	
	public enum SelectFromType
	{
		FromContacts,FromCallLog,FromMessage,Manully;
		static
		{
			SelectFromType[] type = new SelectFromType[4];
			type[0] = FromContacts;	
			type[1]=FromCallLog;
			type[2]=FromMessage;
			type[3]=Manully;
		}
	}


	class ButtonGroup extends LinearLayout
	{
		Button[] mButtonGroup;
		int[] mNameArray;
		boolean markAll;
		ButtonGroupListener btnListener;
	}
}