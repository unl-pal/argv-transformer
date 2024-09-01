package edu.css.cis3334.vogellasampleinclass;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TestDatabaseActivity extends ListActivity {
    private CommentsDataSource datasource;      // our link to the datasource for SQLite access
    private int listPosition = 0;                // currently selected item in the list

}
