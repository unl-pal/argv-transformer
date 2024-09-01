package com.jeremyfeinstein.slidingmenu.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SampleListFragment extends ListFragment {

	private class SampleItem {
		public String tag;
		public int iconRes;
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

	}
}
