package com.grottworkshop.gwsviewmodel.viewmodel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.grottworkshop.gwsviewmodel.viewmodel.view.IUserListView;
import com.grottworkshop.gwsviewmodellibrary.viewmodel.AbstractViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgrott on 3/30/2015.
 */
public class UserListViewModel extends AbstractViewModel<IUserListView> {

    private List<String> mLoadedUsers;

    //Don't persist state variables
    private boolean mLoadingUsers;

    private int numberOfRunningDeletes = 0;
}
