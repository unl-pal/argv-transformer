package com.imiFirewall;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.imiFirewall.Interface.CallEventListener;

import android.app.Service;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


public class imiCallEngine extends PhoneStateListener{
	
	//���� �� ������� 
	private Service mContext;
	
	private CallEventListener mListener;
}