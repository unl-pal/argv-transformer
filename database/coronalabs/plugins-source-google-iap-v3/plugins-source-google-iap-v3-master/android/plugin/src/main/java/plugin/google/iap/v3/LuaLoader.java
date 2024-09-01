// This corresponds to the name of the Lua library,
// e.g. [Lua] require "plugin.library"
package plugin.google.iap.v3;

import android.content.Context;
import android.util.Log;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import plugin.google.iap.v3.util.IabHelper;
import plugin.google.iap.v3.util.IabResult;
import plugin.google.iap.v3.util.Inventory;
import plugin.google.iap.v3.util.Purchase;
import plugin.google.iap.v3.util.SkuDetails;

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

import com.ansca.corona.purchasing.StoreServices;

public class LuaLoader implements JavaFunction {
	private static IabHelper sHelper;

	private int fLibRef;
	private int fListener;
	private IabHelper fHelper;
	private CoronaRuntimeTaskDispatcher fDispatcher;
	private boolean fSetupSuccessful;
	private String fSetupMessage;

	private class InitWrapper implements NamedJavaFunction {
	}

	private class LoadProductsWrapper implements NamedJavaFunction {
	}

	private class PurchaseWrapper implements NamedJavaFunction {
	}

	private class PurchaseSubscriptionWrapper implements NamedJavaFunction {
	}

	private class ConsumePurchaseWrapper implements NamedJavaFunction {
	}

	private class FinishTransactionWrapper implements NamedJavaFunction {
	}

	private class RestoreWrapper implements NamedJavaFunction {
	}
}
