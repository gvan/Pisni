package ua.in.pisni.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.in.pisni.data.repository.SongsRepository;
import ua.in.pisni.data.repository.SongsRepositoryImpl;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    SongsRepository providePoemsRepository(Context context) {
        return new SongsRepositoryImpl(context);
    }

}
