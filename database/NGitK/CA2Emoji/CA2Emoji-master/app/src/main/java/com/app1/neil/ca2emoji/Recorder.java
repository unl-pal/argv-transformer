package com.app1.neil.ca2emoji;

import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Recorder extends Activity {

    final Random rnd = new Random();
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start,stop,play;
}
