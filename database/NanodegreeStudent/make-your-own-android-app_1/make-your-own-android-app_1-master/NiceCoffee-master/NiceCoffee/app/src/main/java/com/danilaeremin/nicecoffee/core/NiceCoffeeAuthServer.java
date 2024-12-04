package com.danilaeremin.nicecoffee.core;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.danilaeremin.nicecoffee.R;
import com.danilaeremin.nicecoffee.data.models.CategoryModel;
import com.danilaeremin.nicecoffee.data.models.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Created by danilaeremin on 10.03.15.
 */

//startService(intent.putExtra("time", 4).putExtra("label", "Call 3") );

public class NiceCoffeeAuthServer extends IntentService {
    public final static String TASK_TYPE = "taskType";
    public final String PRODUCT_ID = "product_id";

    public final static String DB_TIME = "DbTime";

    public final static int UPDATE_DB = 0;
    public final static int FORCE_UPDATE_DB = 1;
    public final static int UPDATE_PRICE = 2;

    final String LOG_TAG = NiceCoffeeAuthServer.class.getSimpleName();

    public static String PRODUCT_LIST = "allProducts.php";
    public static String PRODUCT_WITH_PRODUCT_ID = "productById.php";
    public static String PRODUCT_PRICE_WITH_PRODUCT_ID = "price.php";

    public static String CATEGORY_LIST = "categoryList.php";
    public static String CATEGORY_WITH_PARENT_ID = "subcategoryByParentId.php";

    public static String GET_DEALS = "deals.php";

    public static String UPDATE_DB_TIME = "getTime.php";

    final String API_BASE_URL =
            "http://nicecoffee.ru/api/";
}