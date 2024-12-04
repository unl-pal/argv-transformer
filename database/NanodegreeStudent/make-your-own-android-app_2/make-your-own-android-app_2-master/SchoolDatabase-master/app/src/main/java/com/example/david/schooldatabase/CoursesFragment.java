package com.example.david.schooldatabase;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.david.schooldatabase.data.SchoolDBProvider;

/**
 * Created by david on 3/14/15.
 */
public class CoursesFragment extends Fragment implements LoaderCallbacks<Cursor> {
    private String mCourseStr;
    public static final int COURSE_LOADER =0;
    Uri uri = SchoolDBProvider.CONTENT_URI_2;
    String[] courseData = {SchoolDBProvider._ID, SchoolDBProvider.COURSE_CODE, SchoolDBProvider.COURSE_TITLE, SchoolDBProvider.CREDITS};
    SimpleCursorAdapter mAdapter;
    LoaderManager loadermanager;
    ListView mListView;


}