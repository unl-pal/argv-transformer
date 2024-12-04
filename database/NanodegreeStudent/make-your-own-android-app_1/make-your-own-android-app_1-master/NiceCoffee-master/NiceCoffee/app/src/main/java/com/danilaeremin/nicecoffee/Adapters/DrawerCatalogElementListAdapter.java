package com.danilaeremin.nicecoffee.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danilaeremin.nicecoffee.R;

/**
 * Created by danilaeremin on 08.03.15.
 */
public class DrawerCatalogElementListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private  String[] drawer_catalog_name = new String[]{};
    private  Integer[] drawer_catalog_pics = new Integer[]{};

    private  String[] drawer_main_name = new String[]{};
    private  Integer[] drawer_main_pics = new Integer[]{};

    private  String drawer_head = "";
    private  String drawer_catalog = "";;
}
