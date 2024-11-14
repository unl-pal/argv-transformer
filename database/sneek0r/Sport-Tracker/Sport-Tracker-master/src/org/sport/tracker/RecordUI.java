package org.sport.tracker;

import java.util.Date;

import org.sport.tracker.utils.Record;
import org.sport.tracker.utils.SportTrackerLocationListener;
import org.sport.tracker.utils.TimeCountRunnable;
import org.sport.tracker.utils.Waypoint;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Record activity, create and show new record.
 * 
 * @author Waldemar Smirnow
 *
 */
public class RecordUI extends Activity {

	/**
	 * LocationListener, update database and call updateFields method.
	 */
	public SportTrackerLocationListener locationListener;
	/**
	 * Selected profile.
	 */
	public String profile;
	/**
	 * Time counter Runnable, show record time on textview.
	 */
	Runnable timeCounter = null;
}
