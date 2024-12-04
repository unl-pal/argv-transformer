package com.wernerapps.ezbongo.StopListing;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.wernerapps.ezbongo.DatabaseObjects.Stop;
import com.wernerapps.ezbongo.MainActivity;
import com.wernerapps.ezbongo.Predictions.Prediction;
import com.wernerapps.ezbongo.Predictions.PredictionsFragment;
import com.wernerapps.ezbongo.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopFragment extends Fragment implements StopView {
    public static final String TAG = "StopFragment";

    private ExpandableListView listView;
    private StopPresenterImpl presenter;
    private TextView errorButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ExpandableSwipeAdapter adapter;


}
