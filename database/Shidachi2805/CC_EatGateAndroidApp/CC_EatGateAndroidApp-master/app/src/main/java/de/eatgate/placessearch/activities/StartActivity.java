package de.eatgate.placessearch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.global.AppGob;
import de.eatgate.placessearch.helpers.GPSManager;
import de.eatgate.placessearch.helpers.InternetManager;

/**
 * Einstiegspunkt der App
 * Features: Standortsuche, Ortssuche
 * toDo Ortssuche noch nicht implementiert
 * toDo Auswahl von Rubriken, Auswahl des Radius
 */
public class StartActivity extends Activity {
    private final static String TVLOC = "Ort eingeben";//< Ort eingeben Bsp. Berlin >";
    private final static String TAG = "LOG_STARTACTIVITY";
    private final static String TVTXT = "Suche eingeben"; //< Suche eingeben Bsp. Pizza >";
    private static GPSManager gpsManager;
    private TextView textViewGPS;
    private Button btnToMapActivity;
    private AlertDialog.Builder builder = null;
    private AlertDialog dialog = null;
    private Button btnToListActivity;
    private HashMap<String, Koord> koordinates;
    // private final static String TXT = "TEXTSUCHE";
    // private final static String LOCATION = "STANDORTSUCHE";
    private List<String> names;

    /**
     * Einfacher Dialogbuilder fuer die InfoBox
     */

    class Koord {
        private double lng = 0;
        private double lat = 0;
    }

    public class ListViewAdapter_Locs extends ArrayAdapter<String> {
        private Context context;
        private List<String> tag = new ArrayList<String>();
    }

// class End
}
