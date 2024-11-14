package io.dataspin.analyticsexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.dataspin.analyticsSDK.DataspinManager;

/**
 * Created by rafal on 01.04.15.
 */
public class RegisterUserDialogFragment extends DialogFragment {

    private TextView registerUserName;
    private TextView registerUserSurname;
    private TextView registerUserEmail;
    private TextView registerUserGooglePlusId;
}