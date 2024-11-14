package com.wernerapps.ezbongo.StopListing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.wernerapps.ezbongo.R;

import java.util.List;

public class StopAdapter extends BaseSwipeAdapter {

    private final List<String> items;
    private Context mContext;
}