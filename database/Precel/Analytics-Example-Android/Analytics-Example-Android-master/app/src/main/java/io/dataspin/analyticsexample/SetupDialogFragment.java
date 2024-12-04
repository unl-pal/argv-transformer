package io.dataspin.analyticsexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.dataspin.analyticsSDK.DataspinManager;

/**
 * Created by rafal on 01.04.15.
 */
public class SetupDialogFragment extends DialogFragment {

    private TextView clientName;
    private TextView apiKeyField;
    private TextView versionField;

    public interface NoticeDialogListener {
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
}