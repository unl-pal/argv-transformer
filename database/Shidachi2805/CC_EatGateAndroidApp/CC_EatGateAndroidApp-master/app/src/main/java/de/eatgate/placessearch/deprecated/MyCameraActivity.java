package de.eatgate.placessearch.deprecated;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.eatgate.placessearch.R;

public class MyCameraActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_TAKE_PHOTO = 1;

    private ImageView imageView;
    private String mCurrentPhotoPath;
}