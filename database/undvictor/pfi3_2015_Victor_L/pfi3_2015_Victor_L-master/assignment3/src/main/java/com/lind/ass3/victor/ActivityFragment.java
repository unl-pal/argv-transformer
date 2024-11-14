package com.lind.ass3.victor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.lind.ass3.victor.ParserRelated.Constants;
import com.lind.ass3.victor.ParserRelated.Journey;
import com.lind.ass3.victor.ParserRelated.Journeys;
import com.lind.ass3.victor.ParserRelated.Parser;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private ArrayList<Journey> myItems = new ArrayList<Journey>();
    private ExListAdapter myExListAdapter;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    //And the thread
    private class MyAsyncTask extends AsyncTask<String,Void,Long> {
    }
}