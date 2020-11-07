package com.imiFirewall.activity.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.imiFirewall.PROEntity;
import com.imiFirewall.R;
import com.imiFirewall.common.Commons;
import com.imiFirewall.common.ProgressUtils;
import com.imiFirewall.util.imiProcess;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class ActivityProcess extends Activity
{
	
	public Handler handler;
	public ListView mList;
	public ProcessAdapter adapter;
	public ActivityManager mActivityMgr;
	public int mPosition;
	public ProgressUtils mProgress;
	public ProgressThread mProgressThread;

	
	class ProgressThread extends Thread
	{
		private Handler mHandler;	
	}
	
	
	class ProcessHanlder extends Handler                 //����Handler�����첽ͨѶ����
	{
	}
	
	
	class ProcessAdapter extends BaseAdapter{
				
		private List mContent;
		private LayoutInflater mInflater;
		private PackageManager mPackageMgr;
				
		private class ViewHolder{
			TextView appName;
			ImageView appIcon;
			TextView appSize;
		}
	}
}