package com.wernerapps.ezbongo.Predictions.Reminders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wernerapps.ezbongo.Predictions.Prediction;
import com.wernerapps.ezbongo.R;

import java.io.Serializable;

/**
 * Created by Tom on 3/29/2015.
 */
public class ReminderDialog extends DialogFragment {
    private static final String ARG_1 = "ARG_1";
    public static final String TAG = "ReminderDialog";
    private Serializable prediction;
}
