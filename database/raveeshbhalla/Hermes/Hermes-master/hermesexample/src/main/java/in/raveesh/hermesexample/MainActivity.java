package in.raveesh.hermesexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import in.raveesh.hermes.GcmRegistrationException;
import in.raveesh.hermes.Hermes;
import in.raveesh.hermes.RegistrationCallback;


public class MainActivity extends ActionBarActivity implements RegistrationCallback{

    TextView textView;
}
