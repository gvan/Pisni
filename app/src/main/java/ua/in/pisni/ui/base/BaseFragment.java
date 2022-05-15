package ua.in.pisni.ui.base;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import ua.in.pisni.di.viewmodel_factory.ViewModelFactory;

public abstract class BaseFragment extends DaggerFragment {

    @Inject
    protected ViewModelFactory viewModelFactory;

}
