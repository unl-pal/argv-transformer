package com.danilaeremin.nicecoffee.data.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.danilaeremin.nicecoffee.core.ProductContract.ProductEntry;
import com.danilaeremin.nicecoffee.core.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danilaeremin on 09.03.15.
 */
public class ProductModel {

    private static final String LOG_TAG = ProductModel.class.getSimpleName();

    final String PRODUCT_ID = "productID";
    final String CATEGORY_ID = "categoryID";
    final String PRODUCT_NAME = "name_ru";
    final String PRODUCT_PRICE = "Price";
    final String PRODUCT_BRIEF = "brief_description_ru";
    final String PRODUCT_DESCRIPTION = "description_ru";
    final String PRODUCT_PIC = "picture";

    private Integer productId = 0;
    private String name = "no_name";
    private String brief = "no_brief";
    private String desc = "no_desc";
    private String pic = "no_pic";
    private Integer price = 0;
    private Integer categoryId = 0;
}
