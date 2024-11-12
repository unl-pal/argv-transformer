/*
 * Copyright (C) 2007 The Android Open Source Project
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.imiFirewall.terminal;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import com.imiFirewall.Function;
import com.imiFirewall.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract.Constants;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class Terminal extends Activity {

  
  public static final boolean DEBUG = false;

  public static final boolean LOG_CHARACTERS_FLAG = DEBUG;

  public static final boolean LOG_UNKNOWN_ESCAPE_SEQUENCES = DEBUG;


  public static final String TAG = "Terminal";

  private EmulatorView mEmulatorView;

  
  private TermKeyListener mKeyListener;

 
  private static final int EMULATOR_VIEW = R.id.emulatorView;

  private int mFontSize = 9;
  private int mColorId = 2;
  private int mControlKeyId = 0;
  
  private static final String FONTSIZE_KEY = "fontsize";
  private static final String COLOR_KEY = "color";
  private static final String CONTROLKEY_KEY = "controlkey";
  private static final String SHELL_KEY = "shell";
  private static final String INITIALCOMMAND_KEY = "initialcommand";
 
  public static final int WHITE = 0xffffffff;
  public static final int BLACK = 0xff000000;
  public static final int BLUE = 0xff344ebd;

  private static final int DEFAULT_COLOR_SCHEME = 1;
  private InputMethodManager inputMethod;

  private static final int[][] COLOR_SCHEMES =
      { { BLACK, WHITE }, { WHITE, BLACK }, { WHITE, BLUE } };

  private static final int[] CONTROL_KEY_SCHEMES =
      { KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_AT, KeyEvent.KEYCODE_ALT_LEFT,
          KeyEvent.KEYCODE_ALT_RIGHT };
  private static final String[] CONTROL_KEY_NAME = { "Ball", "@", "Left-Alt", "Right-Alt" };
  private int mControlKeyCode;
  private SharedPreferences mPrefs;
  
  private FileDescriptor mTermFd;
  private FileOutputStream mTermOut;
  private String mShell;
  private String mInitialCommand;
  
}
