package de.eatgate.placessearch.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import de.eatgate.placessearch.entities.GPS;

/**
 * Created by Shi on 22.03.2015.
 */
public class GPSManager implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private final Context mContext;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    Location location = null; // location
    double latitude; // latitude
    double longitude; // longitude
    TextView textViewGPS = null;
    private AlertDialog dialog;
    private GPS gps;

    private interface ICommand {
    }


    // not used
    private class CancelCommand implements ICommand {
        protected Activity m_activity;
    }


    private class EnableGpsCommand extends CancelCommand {
    }
}
