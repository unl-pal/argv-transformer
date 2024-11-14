package de.eatgate.placessearch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.global.AppGob;

public class MakeReviewActivity extends Activity {

    private final String server = "http://192.168.70.22/EatGate/api/WWWBewertungPortal";
    private Button btnSendRev;
    private AlertDialog.Builder builder = null;
    private AlertDialog dialog = null;
    private ProgressDialog statusProgress = null;
    private AppGob app;

    /**
     * Helper class for Registration
     */
    private class SendReview extends AsyncTask<String, Void, Integer> {
        private Activity mActivity;
    }

}
