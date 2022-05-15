package ua.in.pisni.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import ua.in.pisni.App;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        RepositoryModule.class,
        ViewModelModule.class,
        ScreensModule.class
})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder buildApp(App app);

        AppComponent build();
    }

    @Override
    void inject(App instance);

}
