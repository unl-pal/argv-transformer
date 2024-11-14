package com.bashizip.andromed.datahelper;



import java.io.File;

import android.app.AlertDialog;
import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.ConfigScope;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.DatabaseFileLockedException;
import com.db4o.ext.Db4oIOException;


public class DBTool {

	public static ObjectContainer db;
	private static DBTool instance = null;
	
}