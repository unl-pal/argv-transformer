package com.bashizip.andromed.util;

import java.util.List;






import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * @author Patrick Bashizi
 *
 * @param <T>
 */

public abstract class GenericListAdapter<T> extends BaseAdapter{

	
	List<T> list;
	LayoutInflater inflater;
	
	

}
