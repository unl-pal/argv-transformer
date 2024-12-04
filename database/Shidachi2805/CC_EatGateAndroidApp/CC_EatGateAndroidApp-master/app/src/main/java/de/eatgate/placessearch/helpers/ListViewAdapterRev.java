package de.eatgate.placessearch.helpers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.entities.Review;

/**
 * Created by Shi on 14.01.2015.
 */
public class ListViewAdapterRev extends ArrayAdapter<Review> {
    private Context context;
    private List<Review> reviews = new ArrayList<Review>();
}