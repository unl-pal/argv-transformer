package de.eatgate.placessearch.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.entities.GPS;
import de.eatgate.placessearch.entities.Place;
import de.eatgate.placessearch.global.AppGob;
import de.eatgate.placessearch.helpers.InternetManager;
import de.eatgate.placessearch.helpers.ListViewAdapter;
import de.eatgate.placessearch.services.PlaceDetailsService;
import de.eatgate.placessearch.services.PlacesService;

public class PlaceListActivity extends Activity {
    private static final String API_KEY = "AIzaSyAWWG37dcyPNEQNvnP0b-S2-DZxCtKALBY";
    private final static String Str_aktuellePosition = "Hier bist Du";
    GPS gps;
    // Liste der gefundenen Orte, wird vom Asyn PlacesService mit Daten befuellt
    private ArrayList<Place> g_places = new ArrayList<Place>();
    // Details eines Ortes, wird vom Asyn PlaceDetailsService mit Daten befuellt
    private String types = "meal_takeaway|restaurant|meal_delivery";
    private String radius = "1000.0";
    private PlacesService placesService;
    private PlaceDetailsService placeDetailsService;
    private ListView placesListView;
    private ListViewAdapter adapter;

    /**
     * Call Klasse zum Finden der Orte innerhalb des Radius und Types
     */
    private class GetPlaces extends AsyncTask<Void, Void, String> {

        private String types;
        private Context context;
        private String radius;
    }

    /**
     * Call der aufgefuehrt wird, wenn Details zu einem Ort abgefragt werden sollen
     */
    private class GetPlacesDetails extends AsyncTask<Void, Void, String> {

        private String places_id;
        private Context context;
    }
}
