package com.imiFirewall.terminal;


import com.imiFirewall.Function;
import android.os.Bundle;  
import com.imiFirewall.R;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;


class EmulatorView extends View implements OnGestureListener {

  public static final String TAG = "EmulatorView";
  private TranscriptScreen mTranscriptScreen;
  private static final int TRANSCRIPT_ROWS = 10000;
  private int mCharacterWidth;
  private int mCharacterHeight;
  private TextRenderer mTextRenderer;
  private int mTextSize;
  private int mForeground;
  private int mBackground;
  private Paint mCursorPaint;
  private Paint mBackgroundPaint;
  private TerminalEmulator mEmulator;
  private boolean mKnownSize;
  private int mRows;
  private int mColumns;
  private int mVisibleColumns;
  private int mTopRow;
  private int mLeftColumn;
  private FileInputStream mTermIn;
  private FileOutputStream mTermOut;
  private FileDescriptor mTermFd;
  private TermKeyListener mKeyListener;

  private ByteQueue mByteQueue;
  private byte[] mReceiveBuffer=null;

  private static final int UPDATE = 1;
  private Thread mPollingThread;

  private GestureDetector mGestureDetector;

  private float mScrollRemainder;

  private final Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if (msg.what == UPDATE) {
    	  update();
      }
    }
  };
}
