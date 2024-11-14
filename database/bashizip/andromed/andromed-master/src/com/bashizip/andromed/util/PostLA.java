package com.bashizip.andromed.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bashizip.andromed.R;
import com.bashizip.andromed.data.Patient;
import com.bashizip.andromed.data.Post;


public class PostLA extends GenericListAdapter<Post>{

	ViewHolder	holder;
	
	private class ViewHolder {
		public TextView tvNom;
		TextView tvSexe;
		ImageView imageView;
	}

}
