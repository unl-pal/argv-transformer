package com.wernerapps.ezbongo.StopListing;

import com.wernerapps.ezbongo.DatabaseObjects.Stop;
import com.wernerapps.ezbongo.GetAllStops.GetAllStops;
import com.wernerapps.ezbongo.GetAllStops.GetAllStopsImpl;
import com.wernerapps.ezbongo.Predictions.GetPredictions;
import com.wernerapps.ezbongo.Predictions.GetPredictionsImpl;
import com.wernerapps.ezbongo.Predictions.Prediction;

import java.util.List;

/**
 * Created by Tom on 3/26/2015.
 */
public class StopPresenterImpl implements StopPresenter {

    private final StopView stopView;
    private final GetAllStops getAllStopsInteractor;
    private final GetPredictions getPredictionsInteractor;
}
