package com.imiFirewall;

import java.util.ArrayList;

import com.imiFirewall.common.Commons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class TabManagerAdapter extends BaseAdapter{

	private ArrayList mDataArray;
	private Context mContext;
	private LayoutInflater mInflater;
	
	
	public class TabViewHolder {
		TextView ContactName;
		TextView PhoneNumber;
	}
	
}