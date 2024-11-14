/*
 * Copyright (c) 2011, AllSeen Alliance. All rights reserved.
 *
 *    Permission to use, copy, modify, and/or distribute this software for any
 *    purpose with or without fee is hereby granted, provided that the above
 *    copyright notice and this permission notice appear in all copies.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *    WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *    MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *    ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *    WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *    ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *    OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package org.alljoyn.bus.sample.chat;

import org.alljoyn.bus.sample.chat.ChatApplication;
import org.alljoyn.bus.sample.chat.Observable;
import org.alljoyn.bus.sample.chat.Observer;
import org.alljoyn.bus.sample.chat.DialogBuilder;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import android.app.Activity;
import android.app.Dialog;

import android.graphics.Color;
import android.graphics.PorterDuff;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import android.util.Log;

import java.util.List;

public class UseActivity extends Activity implements Observer {
    private static final String TAG = "chat.UseActivity";
    private static final Boolean DEBUG_MODE = false;
    
    public static final int DIALOG_JOIN_ID = 0;
    public static final int DIALOG_LEAVE_ID = 1;
    public static final int DIALOG_ALLJOYN_ERROR_ID = 2;

    private static final int HANDLE_APPLICATION_QUIT_EVENT = 0;
    private static final int HANDLE_HISTORY_CHANGED_EVENT = 1;
    private static final int HANDLE_CHANNEL_STATE_CHANGED_EVENT = 2;
    private static final int HANDLE_ALLJOYN_ERROR_EVENT = 3;
    private static final int HANDLE_CONNECTION_ATTEMPTED_EVENT = 4;
    private static final int HANDLE_CONNECTION_ESTABLISHED_EVENT = 5;
    private static final int HANDLE_SET_DEFAULTS_EVENT = 6;
    
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case HANDLE_APPLICATION_QUIT_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HANDLE_APPLICATION_QUIT_EVENT");
	                finish();
	            }
	            break; 
            case HANDLE_HISTORY_CHANGED_EVENT:
                {
                    Log.i(TAG, "mHandler.handleMessage(): HANDLE_HISTORY_CHANGED_EVENT");
                    updateHistory();
                    break;
                }
            case HANDLE_CHANNEL_STATE_CHANGED_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HANDLE_CHANNEL_STATE_CHANGED_EVENT");
	                updateChannelState();
	                break;
	            }
            case HANDLE_ALLJOYN_ERROR_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HANDLE_ALLJOYN_ERROR_EVENT");
	                alljoynError();
	                break;
	            }
            case HANDLE_CONNECTION_ATTEMPTED_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HANDLE_CONNECTION_ATTEMPTED_EVENT");
	                break;
	            }
            case HANDLE_CONNECTION_ESTABLISHED_EVENT:
	            {
	                Log.i(TAG, "mHandler.handleMessage(): HANDLE_CONNECTION_ESTABLISHED_EVENT");
	                mProgressBar.setVisibility(4);
	                // set the button back to default color
	                mRandomChatButton.setBackgroundResource(android.R.drawable.btn_default);
	                mRandomChatButton.setText("Disconnect From This Chat");
	                mRandomChatButton.setOnClickListener(new View.OnClickListener() {
	                    public void onClick(View v) {
	                    	mChatApplication.useLeaveChannel();
	                        mChatApplication.setAppStatus(ChatApplication.AppStatus.IDLE);
	                        setRandomChatButtonDefaults();
	                    }
	                });
	                break;
	            }
            case HANDLE_SET_DEFAULTS_EVENT:
            	{
            		Log.i(TAG, "mHandler.handleMessage(): HANDLE_SET_DEFAULTS_EVENT");
            		setRandomChatButtonDefaults();
            		break;
            	}
            default:
                break;
            }
        }
    };
    
    private ChatApplication mChatApplication = null;
    
    private ArrayAdapter<String> mHistoryList;
    
    private Button mJoinButton;
    private Button mLeaveButton;
    private Button mQuitButton;
    private Button mRandomChatButton;
    
    private ProgressBar mProgressBar;
    
    private TextView mUseChannelName;
      
    private TextView mUseChannelStatus;
    
    private TextView mHostChannelName;
    private TextView mHostChannelStatus;
}
