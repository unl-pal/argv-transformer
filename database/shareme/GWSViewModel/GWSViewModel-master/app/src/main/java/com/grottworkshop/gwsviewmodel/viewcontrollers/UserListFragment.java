package com.grottworkshop.gwsviewmodel.viewcontrollers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.grottworkshop.gwsviewmodel.R;
import com.grottworkshop.gwsviewmodel.viewmodel.UserListViewModel;
import com.grottworkshop.gwsviewmodel.viewmodel.view.IUserListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by fgrott on 3/31/2015.
 */
public class UserListFragment extends ProjectBaseFragment<IUserListView, UserListViewModel> implements IUserListView {

    @InjectView(android.R.id.progress)
    View mProgressView;
    @InjectView(android.R.id.list)
    ListView mListview;

    private ArrayAdapter<String> mAdapter;
}
