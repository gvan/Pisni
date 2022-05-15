package ua.in.pisni;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import ua.in.pisni.di.DaggerAppComponent;

public class App extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .buildApp(this)
                .build();
    }
}
