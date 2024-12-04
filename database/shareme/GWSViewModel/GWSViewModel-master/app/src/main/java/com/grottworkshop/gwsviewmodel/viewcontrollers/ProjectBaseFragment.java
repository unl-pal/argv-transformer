package com.grottworkshop.gwsviewmodel.viewcontrollers;

import com.grottworkshop.gwsviewmodellibrary.viewcontrollers.ViewModelBaseFragment;
import com.grottworkshop.gwsviewmodellibrary.viewmodel.AbstractViewModel;
import com.grottworkshop.gwsviewmodellibrary.viewmodel.view.IView;

import butterknife.ButterKnife;

/**
 * Created by fgrott on 3/31/2015.
 */
public abstract class ProjectBaseFragment<T extends IView, R extends AbstractViewModel<T>> extends ViewModelBaseFragment<T,R> {
}
