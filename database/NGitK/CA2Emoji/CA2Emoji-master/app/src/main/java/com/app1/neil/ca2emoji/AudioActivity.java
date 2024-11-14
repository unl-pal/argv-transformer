package com.app1.neil.ca2emoji;

        import android.content.pm.PackageManager;
        import android.media.MediaPlayer;
        import android.media.MediaRecorder;
        import android.os.Bundle;
        import android.os.Environment;
        import android.app.Activity;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.File;
        import java.io.IOException;


public class AudioActivity extends Activity {
    MediaRecorder myRecorder;
    MediaPlayer myPlayer;
    String outputFile = null;
    Button startBtn, stopBtn, playBtn, stopPlayBtn;
    private boolean isRecording = false;
    TextView tv1;

    }


