package com.danilaeremin.nicecoffee.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danilaeremin.nicecoffee.MainActivity;
import com.danilaeremin.nicecoffee.R;
import com.danilaeremin.nicecoffee.core.ProductContract;
import com.danilaeremin.nicecoffee.core.Utils;
import com.danilaeremin.nicecoffee.data.models.ProductModel;

/**
 * Created by danilaeremin on 09.03.15.
 */
public class ProductFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_PRODUCT_ID = "product_id";

    private static final String SAVED_PRODUCT_ID = "Saved_product_id";

    private static final int PRODUCT_DATA_LOADER = 0;

    private int product_id;

    private TextView mProductName = null;
    private TextView mProductDesc = null;
    private ImageView mProductPic = null;
}
