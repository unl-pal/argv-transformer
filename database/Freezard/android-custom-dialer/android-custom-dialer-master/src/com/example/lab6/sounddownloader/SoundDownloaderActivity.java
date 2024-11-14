package com.example.lab6.sounddownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.lab6.R;
import com.example.lab6.utils.Utils;
import com.example.lab6.utils.ZIP;

/**
 * SoundDownloaderActivity.java
 * 
 * Starts a WebView in the app that shows a download page for voice categories.
 * If user clicks any link, the archive file is downloaded and extracted to the
 * external storage in a background thread.
 */
public class SoundDownloaderActivity extends Activity implements
		OnSharedPreferenceChangeListener {
	// Default locations
	public static final String DEFAULT_DOWNLOAD_LOCATION = "http://dt031g.programvaruteknik.nu/dialpad/sounds/";
	public static final String DEFAULT_STORAGE_LOCATION = Environment.getExternalStorageDirectory()
			+ "/dialpad/sounds/";

	private WebView webView;
	private ProgressDialog progressDialog;
	private String downloadLocation;
	private String storageLocation;
	
	private class MyWebViewClient extends WebViewClient implements
			DownloadListener {

		// Background thread for downloading and decompressing archive files
		private class DownloadArchiveTask extends
				AsyncTask<String, Integer, String> {
		}
	}
}