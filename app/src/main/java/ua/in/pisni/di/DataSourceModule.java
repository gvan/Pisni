package ua.in.pisni.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.in.pisni.data.source.preferences.SongsPreferences;
import ua.in.pisni.data.source.preferences.SongsPreferencesImpl;

@Module
public class DataSourceModule {

    @Singleton
    @Provides
    SongsPreferences provideSongsPreferences(Context context) {
        return new SongsPreferencesImpl(context);
    }

}
