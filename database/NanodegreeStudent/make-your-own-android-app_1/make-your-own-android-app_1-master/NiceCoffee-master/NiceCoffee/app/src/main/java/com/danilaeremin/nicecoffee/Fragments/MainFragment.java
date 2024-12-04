package com.danilaeremin.nicecoffee.Fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewFlipper;

import com.danilaeremin.nicecoffee.AboutUsActivity;
import com.danilaeremin.nicecoffee.Adapters.CategoryGridAdapter;
import com.danilaeremin.nicecoffee.Adapters.DataAdapter;
import com.danilaeremin.nicecoffee.MainActivity;
import com.danilaeremin.nicecoffee.R;
import com.danilaeremin.nicecoffee.core.Utils;

/**
 * Created by danilaeremin on 08.03.15.
 */
public class MainFragment extends Fragment implements GestureDetector.OnGestureListener {
    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_DISTANCE_Y = 50;

    private static final String SAVED_STATE = "Saved_state";

    private ViewFlipper mFlipper;
    private GestureDetector detecture;

    private int mCurrentPosition = -1;


    private GridView mMainGrid = null;
    private GridView mCategoryGrid = null;
    private DataAdapter mMainGridAdapter = null;
    private CategoryGridAdapter mCategoryGridAdapter = null;

        private class LoadStaticData extends AsyncTask<Void, Void, Void> {
    }
}
