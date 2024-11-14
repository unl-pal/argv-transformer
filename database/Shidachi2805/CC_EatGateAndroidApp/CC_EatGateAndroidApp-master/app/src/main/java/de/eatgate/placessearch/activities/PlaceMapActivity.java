package de.eatgate.placessearch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.entities.GPS;
import de.eatgate.placessearch.entities.Place;
import de.eatgate.placessearch.entities.PlaceDetails;
import de.eatgate.placessearch.global.AppGob;
import de.eatgate.placessearch.helpers.InternetManager;
import de.eatgate.placessearch.services.PlaceDetailsService;
import de.eatgate.placessearch.services.PlacesService;


/**
 * Created by ProMarkt on 18.01.2015.
 */
public class PlaceMapActivity extends Activity implements OnMapReadyCallback {

    private static final String API_KEY = "AIzaSyAWWG37dcyPNEQNvnP0b-S2-DZxCtKALBY";
    private final static String STR_AKTUELLEPOSITION = "Hier bist Du";
    private final static String STR_LOCATION = "Startpunkt";
    private final String TAG = "LOG_PLACEMAPACTIVITY";
    private final String YOURPOSTXT = "Deine aktuelle Position";
    private final int ZOOMFAKTOR = 13;
    private GPS gps;
    // Liste der gefundenen Orte, wird vom Asyn PlacesService mit Daten befuellt
    private ArrayList<Place> allFoundPlaces = null;
    // Map speichert die Relation markerId, place_id;
    private HashMap<String, String> marker_id_place_id_map = null;
    private Marker markerOpenInfoWindow = null;
    private String types = "meal_takeaway|restaurant|meal_delivery";
    private String short_radius = "1200.0";
    private String long_radius = "5000.0";
    private String radius = "1200.0";
    private String search_word = "";
    private String location_word = "";
    private PlacesService placesService = null;
    private PlaceDetailsService placeDetailsService = null;
    private GoogleMap meinGoogleMap = null;
    private ProgressDialog statusProgress = null;
    private AlertDialog.Builder builder = null;
    private AlertDialog dialog = null;
    private AppGob appGob = null;
    private double lng = 0;
    private double lat = 0;
    // private final static String TXT = "TEXTSUCHE";
    // private final static String LOCATION = "STANDORTSUCHE";

    /**
     * Call Klasse zum Finden der Orte innerhalb des Radius und Types
     */
    private class GetPlaces extends AsyncTask<Void, Void, String> {

        private String types;
        private String radius;
        private String searchWord;
        private double longitude;
        private double latitude;
    }
}
