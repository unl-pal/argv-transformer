package com.danilaeremin.nicecoffee.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.danilaeremin.nicecoffee.core.ProductContract.CategoriesEntry;
import com.danilaeremin.nicecoffee.core.ProductContract.ProductEntry;

/**
 * Created by danilaeremin on 09.03.15.
 */
public class dbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "products.db";
}
