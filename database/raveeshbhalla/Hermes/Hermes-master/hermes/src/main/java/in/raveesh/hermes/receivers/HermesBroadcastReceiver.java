package in.raveesh.hermes.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import in.raveesh.hermes.Hermes;

public class HermesBroadcastReceiver extends WakefulBroadcastReceiver {

    protected boolean gotRegistrationId = false;
    protected boolean error = false;
    protected String registrationID;
}
