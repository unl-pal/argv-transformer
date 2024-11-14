package in.raveesh.hermes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import in.raveesh.hermes.receivers.ExponentialBackoffReceiver;

/**
 * Created by Raveesh on 31/03/15.
 */
public class Hermes {

    public static final String TAG = "Hermes";

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static final int DEFAULT_BACKOFF = 60000;
    public static String SHARED_PREFERENCES_FILENAME = "HermesFileName";

    private static String regID;
    private static GoogleCloudMessaging gcm;
    private static String SENDER_ID = "";
    private static SharedPreferences sharedPreferences;
    public static RegistrationCallback CALLBACK;
    private static int delay = DEFAULT_BACKOFF;
}
