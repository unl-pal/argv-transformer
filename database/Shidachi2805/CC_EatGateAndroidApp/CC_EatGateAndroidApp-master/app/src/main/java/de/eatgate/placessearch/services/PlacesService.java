package de.eatgate.placessearch.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import de.eatgate.placessearch.entities.Place;

/**
 *  Create request for Places API.

 *
 */
public class PlacesService {

    private final String TAG = "LOG_PLACESSERVICE";
    private String API_KEY;
    private String radius;
    private String types;
    private String searchWord;
    private String name;
}