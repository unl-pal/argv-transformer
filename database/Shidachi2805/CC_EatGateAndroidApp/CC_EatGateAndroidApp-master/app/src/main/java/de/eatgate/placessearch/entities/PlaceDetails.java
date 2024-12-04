package de.eatgate.placessearch.entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ProMarkt on 19.01.2015.
 */
public class PlaceDetails {
    private double rating;
    private String icon;
    private String place_id;
    private String name;
    private String formatted_address;
    private String vicinity;
    private String openhours;
    private ArrayList<String> weekdays;
    private ArrayList<Review> arrRev;


}
