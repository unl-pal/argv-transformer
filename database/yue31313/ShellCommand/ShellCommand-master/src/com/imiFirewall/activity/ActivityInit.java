package com.imiFirewall.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Date;
import java.util.HashMap;

import com.imiFirewall.R;
import com.imiFirewall.imiNetdevice;
import com.imiFirewall.imiSql;
import com.imiFirewall.R.layout;
import com.imiFirewall.R.string;
import com.imiFirewall.service.NetService;
import com.imiFirewall.util.imiUtil;
  
  
public class ActivityInit extends Activity{
  
    private imiSql db;
    private String device_mobile[];
    private String device_wifi[];
    private Handler initHandler;
    private long init_data_mobile[];
    private long init_data_wifi[];
    private long last_data_mobile[];
    private long last_data_wifi[];   
    public ProgressDialog progressDialog;
    
    class InitHandler extends Handler{
    }
   
    private class InitThread extends Thread{
    }
 }
    