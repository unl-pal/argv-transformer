package com.danilaeremin.nicecoffee.core;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.danilaeremin.nicecoffee.core.ProductContract.CategoriesEntry;
import com.danilaeremin.nicecoffee.core.ProductContract.ProductEntry;

/**
 * Created by danilaeremin on 09.03.15.
 */
public class ProductProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private dbHelper mOpenHelper;

    private static final int PRODUCTS = 100;
    private static final int PRODUCT_WITH_PRODUCT_ID = 101;
    private static final int PRODUCT_WITH_CATEGORY_ID = 102;
    private static final int CATEGORIES = 200;
    private static final int CATEGORY_WITH_CATEGORY_ID = 201;
    private static final int CATEGORY_WITH_PARENT_ID = 202;

    private static final SQLiteQueryBuilder sProductListQueryBuilder;
    private static final SQLiteQueryBuilder sCategoryListQueryBuilder;

    private static final SQLiteQueryBuilder sProductListWithParentCategoryIdQueryBuilder;

    static{
        sProductListQueryBuilder = new SQLiteQueryBuilder();
        sProductListQueryBuilder.setTables(
                ProductEntry.TABLE_NAME
                );

        sCategoryListQueryBuilder = new SQLiteQueryBuilder();
        sCategoryListQueryBuilder.setTables(CategoriesEntry.TABLE_NAME);

        sProductListWithParentCategoryIdQueryBuilder = new SQLiteQueryBuilder();
        sProductListWithParentCategoryIdQueryBuilder.setTables(
                ProductEntry.TABLE_NAME + " AS p" +
                    " LEFT OUTER JOIN " + CategoriesEntry.TABLE_NAME + " AS c1 ON (" +
                        "p." + ProductEntry.COLUMN_PRODUCT_CATEGORY + " = c1." + CategoriesEntry.COLUMN_CATEGORY_ID + ")" +
                    " LEFT OUTER JOIN " + CategoriesEntry.TABLE_NAME + " AS c2 ON (" +
                        "c1." + CategoriesEntry.COLUMN_PARENT_ID    + " = c2." + CategoriesEntry.COLUMN_CATEGORY_ID + ")" +
                    " LEFT OUTER JOIN " + CategoriesEntry.TABLE_NAME + " AS c3 ON (" +
                        "c2." + CategoriesEntry.COLUMN_PARENT_ID    + " = c3." + CategoriesEntry.COLUMN_CATEGORY_ID + ")"
        );
    }

    private static final String sProducts = "";
    private static final String sProductWithProductId =
            ProductEntry.TABLE_NAME + "." + ProductEntry.COLUMN_PRODUCT_ID + " = ? ";
    private static final String sProductWithCategoryId =
            "c1." + CategoriesEntry.COLUMN_CATEGORY_ID + " = ? OR "+
            "c2." + CategoriesEntry.COLUMN_CATEGORY_ID + " = ? OR "+
            "c3." + CategoriesEntry.COLUMN_CATEGORY_ID + " = ? OR "+
            "c3." + CategoriesEntry.COLUMN_PARENT_ID + " = ?";

    private static final String sCategoryList =
            CategoriesEntry.TABLE_NAME;
    private static final String sCategoryWithCategoryId =
            CategoriesEntry.TABLE_NAME + "." + CategoriesEntry.COLUMN_CATEGORY_ID + " = ? ";
    private static final String sCategoryWithParentId =
            CategoriesEntry.TABLE_NAME + "." + CategoriesEntry.COLUMN_PARENT_ID + " = ? ";
}
