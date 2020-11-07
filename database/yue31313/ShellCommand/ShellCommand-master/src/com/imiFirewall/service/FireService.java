package com.imiFirewall.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import com.android.internal.telephony.ITelephony;
import com.imiFirewall.Interface.CallEventListener;
import com.imiFirewall.activity.ActivityMain;
import com.imiFirewall.activity.ActivitySplash;
import com.imiFirewall.common.Commons;

import com.imiFirewall.R;
import com.imiFirewall.SpamCharacter;
import com.imiFirewall.imiCallEngine;
import com.imiFirewall.imiSql;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.Contacts;
import android.telephony.TelephonyManager;
import android.util.Log;



public class FireService extends Service implements CallEventListener
{

	private imiCallEngine mCallEngine;
	private boolean mVIPNeedDeleteLog = false;
	private String mVIPIncomingNumber;
	private imiSql db=null;	
	private SpamCharacter mSpamCharacter;
	
}