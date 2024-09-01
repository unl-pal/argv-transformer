// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.google.iap.v3;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import plugin.google.iap.v3.util.IabHelper;
import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Purchase;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.NamedJavaFunction;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.ansca.corona.CoronaRuntimeTask;

public class PurchaseFinishedListener implements IabHelper.OnIabPurchaseFinishedListener {
	// This listener is needed for the Adrally plugin so that we can register a successful in app purchase
	private static CopyOnWriteArrayList<IabHelper.OnIabPurchaseFinishedListener> sListeners;

	static {
		sListeners = new CopyOnWriteArrayList<IabHelper.OnIabPurchaseFinishedListener>();
	}

	private CoronaRuntimeTaskDispatcher fDispatcher;
	private int fListener;
}