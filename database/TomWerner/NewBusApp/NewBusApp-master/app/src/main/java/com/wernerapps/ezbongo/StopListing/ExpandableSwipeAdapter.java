package com.wernerapps.ezbongo.StopListing;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.wernerapps.ezbongo.DatabaseObjects.Stop;
import com.wernerapps.ezbongo.R;

import java.util.List;

import static com.wernerapps.ezbongo.StopListing.StopPresenter.*;

public class ExpandableSwipeAdapter extends BaseExpandableListAdapter
{
    private final Context context;
    private final List<String> titles;
    private final List<List<Stop>> items;
    private final StopPresenter presenter;
}