package com.danilaeremin.nicecoffee.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.danilaeremin.nicecoffee.Adapters.ProductCellAdapter;
import com.danilaeremin.nicecoffee.Adapters.SubcategoryAdapter;
import com.danilaeremin.nicecoffee.MainActivity;
import com.danilaeremin.nicecoffee.R;
import com.danilaeremin.nicecoffee.core.ProductContract;
import com.danilaeremin.nicecoffee.core.Utils;
import com.danilaeremin.nicecoffee.data.models.CategoryModel;

/**
 * Created by danilaeremin on 09.03.15.
 */
public class CategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    final String LOG_TAG = CategoryFragment.class.getSimpleName();

    private static final String ARG_CATEGORY_ID = "category_id";

    private static final String SAVED_CATEGORY_ID = "Saved_category_id";

    private static final int PRODUCT_LIST_LOADER = 0;
    private static final int SUBCATEGORY_LIST_LIST_LOADER = 1;
    private static final int HEAD_LOADER = 2;

    private Integer category_id;
    private int subcategory_id;
    private int product_id = -1;


    private ProductCellAdapter mProductCellAdapter;
    private SubcategoryAdapter mSubcategoryAdapter;

//    public static int convertDpToPixels(float dp, Context context){
//        Resources resources = context.getResources();
//        return (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                dp,
//                resources.getDisplayMetrics()
//        );
//    }
}
