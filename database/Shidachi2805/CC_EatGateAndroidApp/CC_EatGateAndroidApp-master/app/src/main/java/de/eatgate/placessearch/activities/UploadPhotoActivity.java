package de.eatgate.placessearch.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.global.AppGob;
import de.eatgate.placessearch.helpers.MultipartEntity;

public class UploadPhotoActivity extends Activity implements OnClickListener {
    private static final String TAG = "upload";
    private Button mTakePhoto;
    private String mCurrentPhotoPath;
    private ProgressDialog statusProgress = null;
    private AlertDialog.Builder builder = null;
    private AlertDialog dialog = null;
    private String server = "http://192.168.70.22/EatGate/home/photoupload";
    private AppGob app = null;

    private class UploadTask extends AsyncTask<Integer, Void, Integer> {
    }
}
