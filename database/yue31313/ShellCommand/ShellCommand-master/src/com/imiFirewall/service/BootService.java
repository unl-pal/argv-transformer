package com.imiFirewall.service;

import android.app.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.*;
import android.util.Log;
import java.util.HashMap;
import java.util.Date;

import com.imiFirewall.imiSql;
import com.imiFirewall.util.imiUtil;



// Referenced classes of package com.aob.android.mnm:
//            DataBase, InitActivity

public class BootService extends Service
{

    private imiSql mydb;
    private Handler handler;
    private HashMap map;
    private Runnable runnable;
    private String where[][];
    
    final private String TAG="imiFirewall_clean";
    
    private class BootRun implements Runnable{
		
	}

}