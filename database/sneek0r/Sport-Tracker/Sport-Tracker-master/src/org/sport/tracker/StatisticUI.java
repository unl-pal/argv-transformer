package org.sport.tracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.sport.tracker.utils.Record;
import org.sport.tracker.utils.RecordDBHelper;
import org.sport.tracker.utils.RecordsListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Statistic activity, show total time, avarage speed and  all records from selected profile.
 *  
 * @author Waldemar Smirnow
 *
 */
public class StatisticUI extends Activity {
	
	/**
	 * Record profile.
	 */
	public String profile;
}
