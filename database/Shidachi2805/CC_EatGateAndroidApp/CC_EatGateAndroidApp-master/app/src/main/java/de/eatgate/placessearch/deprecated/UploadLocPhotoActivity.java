package de.eatgate.placessearch.deprecated;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.eatgate.placessearch.R;
import de.eatgate.placessearch.global.AppGob;

public class UploadLocPhotoActivity extends ActionBarActivity {

    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    String upLoadServerUri = null;

    /**
     * *******  File Path ************
     */
    private String uploadFilePath = "";// "/mnt/sdcard/";
}