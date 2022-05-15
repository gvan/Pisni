package ua.in.pisni.di;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ua.in.pisni.di.viewmodel_factory.ViewModelKey;
import ua.in.pisni.ui.categories.CategoriesViewModel;
import ua.in.pisni.ui.song.SongViewModel;
import ua.in.pisni.ui.songs.SongsViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel.class)
    abstract ViewModel bindCategoriesViewModel(CategoriesViewModel categoriesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SongsViewModel.class)
    abstract ViewModel bindSongsViewModel(SongsViewModel songsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SongViewModel.class)
    abstract ViewModel bindSongViewModel(SongViewModel songViewModel);

}
