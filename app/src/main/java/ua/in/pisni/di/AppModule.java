package ua.in.pisni.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ua.in.pisni.App;

@Module
public class AppModule {

    @Provides
    Context provideContext(App app) {
        return app.getApplicationContext();
    }

}
