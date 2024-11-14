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
public class EnrollmentFragment extends Fragment implements LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = EnrollmentFragment.class.getSimpleName();
    private static final String ENROLLMENT_SHARE_HASHTAG = " #SchoolDatabaseApp";
    private String mEnrollmentStr;
    public static final int ENROLLMENT_LOADER =0;
    Uri uri = SchoolDBProvider.CONTENT_URI_3;
    String[] enrollmentData = {SchoolDBProvider._ID, SchoolDBProvider.INDEX_NO, SchoolDBProvider.ACADEMIC_YEAR, SchoolDBProvider.SEMESTER, SchoolDBProvider.COURSE_CODE};
    SimpleCursorAdapter mAdapter;
    LoaderManager loadermanager;
    ListView mListView;


}