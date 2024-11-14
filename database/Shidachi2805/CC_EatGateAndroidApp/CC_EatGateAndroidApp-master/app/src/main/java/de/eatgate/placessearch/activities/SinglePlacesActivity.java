package de.eatgate.placessearch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.deprecated.EatGateReviewService;
import de.eatgate.placessearch.entities.EatGatePhotoUrl;
import de.eatgate.placessearch.entities.EatGateReview;
import de.eatgate.placessearch.entities.Review;
import de.eatgate.placessearch.global.AppGob;
import de.eatgate.placessearch.helpers.InternetManager;
import de.eatgate.placessearch.helpers.ListViewAdapterRev;

/**
 * Created by Khanh on 19.01.2015.
 */
public class SinglePlacesActivity extends Activity {
    private final static int TIMEOUT_CON = 600;
    private final static int TIMEOUT_SOC = 1000;
    private static ArrayList<EatGateReview> eatGateReviewList = null;
    private static ArrayList<EatGatePhotoUrl> eatGatePhotoList = null;
    private final String server = "http://192.168.70.22/EatGate/api/WWWBewertungPortal";
    private final String serverDownload = "http://192.168.70.22/";
    private final String TAG = "LOG_SINGLEPLACESACTIVITY";
    private ArrayAdapter<String> listAdapter;
    private ListViewAdapterRev adapter;
    private AlertDialog.Builder builder = null;
    private AlertDialog dialog = null;
    private ImageView mImage;
    private Button btnImgs;
    private TextView mText;
    private ProgressDialog statusProgress = null;
    private int imgPos;
    private EatGateReviewService eatGateReviewService = null;
    private AppGob app;
    private String g_placeId = "";
    private String g_addresse = "";
    private String g_rating = "";
    private String g_loc_name = "";

    private class DownloadTask extends AsyncTask<String, Void, Bitmap> {
        private Activity mActivity;
    }


    /**
     * Klasse fuer Webservice zum Anfordern der EatGateReviews
     */
    private class GetEatGateReviews extends AsyncTask<String, Void, Integer> {

        private Activity mActivity;
    }

    /**
     * Klasse fuer Webservice für EatGatePhotos Pfad
     */
    private class GetEatGatePhotos extends AsyncTask<String, Void, Integer> {

        private Activity mActivity;
    }


    /**
     * Prueft ob Location in DB von EatGate, falls nicht erfolgt Eintrag
     */
    private class CheckEatGatePlace extends AsyncTask<String, Void, Integer> {
        private Activity mActivity;
        private String mPlace_id;
    }

}




