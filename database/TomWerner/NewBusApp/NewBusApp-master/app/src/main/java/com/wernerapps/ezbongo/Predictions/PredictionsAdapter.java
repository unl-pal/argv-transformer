package com.wernerapps.ezbongo.Predictions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wernerapps.ezbongo.R;

import java.util.List;

/**
 * Created by Tom on 3/29/2015.
 */
public class PredictionsAdapter extends BaseAdapter {

    private final List<Prediction> predictions;
    private final Context context;
    private final PredictionsPresenter presenter;
}
