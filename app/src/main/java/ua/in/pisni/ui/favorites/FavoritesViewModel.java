package ua.in.pisni.ui.favorites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ua.in.pisni.data.model.Song;
import ua.in.pisni.data.repository.SongsRepository;
import ua.in.pisni.data.source.preferences.SongsPreferences;

public class FavoritesViewModel extends ViewModel {

    private SongsRepository songsRepository;
    private SongsPreferences songsPreferences;

    private MutableLiveData<List<Song>> songsLiveData = new MutableLiveData<>();

    @Inject
    public FavoritesViewModel(SongsRepository songsRepository, SongsPreferences songsPreferences) {
        this.songsRepository = songsRepository;
        this.songsPreferences = songsPreferences;
    }

    public void initData(){
        List<Integer> favoriteIds = songsPreferences.getFavoriteSongs();
        List<Song> favoriteSongs = songsRepository.getSongsByIds(favoriteIds);
        songsLiveData.setValue(favoriteSongs);
    }

    public MutableLiveData<List<Song>> getSongsLiveData() {
        return songsLiveData;
    }
}
