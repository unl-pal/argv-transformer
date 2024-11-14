package com.wernerapps.ezbongo.Predictions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.wernerapps.ezbongo.MainActivity;
import com.wernerapps.ezbongo.Predictions.Reminders.ReminderDialog;
import com.wernerapps.ezbongo.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PredictionsFragment extends Fragment implements PredictionsView {
    public static final String TAG = "PredictionsFragment";

    private PredictionsPresenter presenter;
    private TextView errorButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String ARG_1 = "ARG_1";
    private static final String ARG_2 = "ARG_2";
    private String stopId;
    private AbsListView listView;
}