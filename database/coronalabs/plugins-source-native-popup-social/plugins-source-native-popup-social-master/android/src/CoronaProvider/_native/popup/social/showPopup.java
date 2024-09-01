//
//  showPopup.java
//  Social Plugin
//
//  Copyright (c) 2013 Coronalabs. All rights reserved.
//

// Package name
package CoronaProvider._native.popup.social;

// Java Imports
import java.util.*;

// Android Imports
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.content.pm.ResolveInfo;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Parcelable;

// JNLua imports
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

// Corona Imports
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.ansca.corona.storage.FileContentProvider;
import com.ansca.corona.storage.FileServices;

/**
 * Implements the showPopup() function in Lua.
 * <p>
 * Show's a chooser dialog.
 */
public class showPopup implements com.naef.jnlua.NamedJavaFunction 
{
	// Event task
	private static class RaisePopupResultEventTask implements CoronaRuntimeTask 
	{
		private int fLuaListenerRegistryId;
		private int fResultCode;
	}
	
	// Our lua callback listener
 	private int fListener;
	private Intent sharingIntent;
}
