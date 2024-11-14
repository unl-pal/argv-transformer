package io.dataspin.analyticsexample;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import io.rwilinski.tracely.*;

import org.w3c.dom.Text;

import java.util.List;

import io.dataspin.analyticsSDK.*;


public class AnalyticsActivity extends ActionBarActivity implements IDataspinListener, SetupDialogFragment.NoticeDialogListener {

    private Spinner itemsSpinner;
    private Spinner eventsSpinner;
    private TextView configLabel;
    private TextView statusLabel;
    private int currentItemIndex;
    private int currentEventIndex;
    private TextView apiKeyLabel;
    private TextView clientNameLabel;

    private Button setupButton;
    private Button registerUserButton;
    private Button registerConfirmButton;
    private Button registerDeviceButton;
    private Button startSessionButton;
    private Button endSessionButton;
    private Button getItemsButton;
    private Button getEventsButton;
    private Button buyButton;
    private Button registerCustomEventButton;
    private Button getBacklogTasksButton;
}
