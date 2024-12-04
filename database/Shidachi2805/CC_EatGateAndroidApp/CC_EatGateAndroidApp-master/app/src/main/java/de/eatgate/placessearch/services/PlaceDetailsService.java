package de.eatgate.placessearch.services;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.eatgate.placessearch.entities.PlaceDetails;

/**
 * Created by ProMarkt on 19.01.2015.
 */
public class PlaceDetailsService {

    private final String place_id;
    private String API_KEY;
}