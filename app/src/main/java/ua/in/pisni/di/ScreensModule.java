package ua.in.pisni.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.in.pisni.ui.categories.CategoriesFragment;
import ua.in.pisni.ui.song.SongFragment;
import ua.in.pisni.ui.songs.SongsFragment;

@Module
public abstract class ScreensModule {

    @ContributesAndroidInjector
    abstract CategoriesFragment bindCategoriesFragment();

    @ContributesAndroidInjector
    abstract SongsFragment bindSongsFragment();

    @ContributesAndroidInjector
    abstract SongFragment bindSongFragment();

}
