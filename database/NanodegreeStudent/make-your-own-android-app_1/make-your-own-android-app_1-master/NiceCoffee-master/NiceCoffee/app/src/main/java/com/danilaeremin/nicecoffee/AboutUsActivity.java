package com.danilaeremin.nicecoffee;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.danilaeremin.nicecoffee.Fragments.AboutUsFragment;
import com.danilaeremin.nicecoffee.Fragments.CategoryFragment;
import com.danilaeremin.nicecoffee.Fragments.MainFragment;
import com.danilaeremin.nicecoffee.Fragments.NavigationDrawerFragment;
import com.danilaeremin.nicecoffee.core.NiceCoffeeAuthServer;
import com.danilaeremin.nicecoffee.core.Utils;

/**
 * Created by danilaeremin on 14.03.15.
 */
public class AboutUsActivity extends ActionBarActivity {

    final String LOG_TAG = ActionBarActivity.class.getSimpleName();
}