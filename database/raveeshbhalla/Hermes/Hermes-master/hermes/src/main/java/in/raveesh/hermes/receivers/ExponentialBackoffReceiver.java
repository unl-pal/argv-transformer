package in.raveesh.hermes.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import in.raveesh.hermes.Hermes;

public class ExponentialBackoffReceiver extends BroadcastReceiver {
    private static String ACTION = "in.raveesh.hermes.receivers.EXPONENTIAL_BACKOFF";
    private static String EXTRA_GAP = "GAP";
    private static String EXTRA_SENDER_ID = "SENDER_ID";
}
