package org.sport.tracker.utils;

import java.util.Date;
import java.util.List;

import org.sport.tracker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Record ListAdapter. Fill List entries with record data.
 * 
 * @author Waldemar Smirnow
 *
 */
public class RecordsListAdapter extends ArrayAdapter<Record> {

	/**
	 * Context.
	 */
	Context context;
	/**
	 * List with (record) data.
	 */
	List<Record> records;
}
