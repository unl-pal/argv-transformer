package com.danilaeremin.nicecoffee.data.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.danilaeremin.nicecoffee.core.ProductContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danilaeremin on 10.03.15.
 */
public class CategoryModel {

    private static final String LOG_TAG = CategoryModel.class.getSimpleName();

    final String PARENT_ID = "parent";
    final String CATEGORY_ID = "categoryID";
    final String CATEGORY_NAME = "name_ru";
    final String CATEGORY_PIC = "picture";

    private Integer categoryId = 0;
    private String name = "no_name";
    private Integer parentId = 0;
    private String pic = "no_pic";
}
