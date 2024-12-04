package com.wernerapps.ezbongo.BongoAPI;

import com.wernerapps.ezbongo.DatabaseObjects.Route;
import com.wernerapps.ezbongo.DatabaseObjects.Stop;
import com.wernerapps.ezbongo.Predictions.Prediction;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Tom on 3/28/2015.
 */
public interface BongoService {
    public static final String API_URL = "http://api.ebongo.org";
    public static final String API_KEY = "hfjiLcSjgJUQnhqn1WpGu4AfKBZX7Eo1";
}
