package com.bashizip.andromed;

import java.util.ArrayList;
import java.util.List;

import com.bashizip.andromed.R;
import com.bashizip.andromed.application.AndromedApplication;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.datahelper.DBTool;

import com.bashizip.andromed.util.PostLA;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class PostList extends Activity {

	ListView lvPosts;
	List<Post> posts = new ArrayList<Post>();

	private DBTool dbcon;
	PostLA adapter;

	// Menu item ids
	public static final int MENU_ITEM_DELETE = Menu.FIRST;
	public static final int MENU_ITEM_INSERT = Menu.FIRST + 1;

}
