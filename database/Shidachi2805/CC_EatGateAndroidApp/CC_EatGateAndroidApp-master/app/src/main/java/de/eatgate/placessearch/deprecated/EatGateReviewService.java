package de.eatgate.placessearch.deprecated;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.eatgate.placessearch.entities.EatGateReview;


/**
 * Created by Shi on 30.03.2015.
 */
public class EatGateReviewService {
    private final String server = "http://192.168.70.22/EatGate/api/WWWBewertungPortal?Service=ReadBewertung";
}
