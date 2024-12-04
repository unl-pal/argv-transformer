package com.danilaeremin.nicecoffee.Fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.danilaeremin.nicecoffee.AboutUsActivity;
import com.danilaeremin.nicecoffee.Adapters.CategoryGridAdapter;
import com.danilaeremin.nicecoffee.Adapters.DataAdapter;
import com.danilaeremin.nicecoffee.MainActivity;
import com.danilaeremin.nicecoffee.R;
import com.danilaeremin.nicecoffee.core.Utils;

/**
 * Created by danilaeremin on 09.03.15.
 */
public class AboutUsFragment extends Fragment {
    private static final String LOG_TAG = AboutUsFragment.class.getSimpleName();
}