package ua.in.pisni.di.viewmodel_factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> provider = getProvider(creators, modelClass);
        return (T) provider.get();
    }

    private Provider<? extends ViewModel> getProvider(Map<Class<? extends ViewModel>, Provider<ViewModel>> map, Class<?> key) {
        for(Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : map.entrySet()) {
            if(key.isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
