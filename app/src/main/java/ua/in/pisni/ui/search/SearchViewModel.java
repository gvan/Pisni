package ua.in.pisni.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.model.Song;
import ua.in.pisni.data.repository.SongsRepository;

public class SearchViewModel extends ViewModel {

    private SongsRepository songsRepository;

    private MutableLiveData<List<Song>> songsLiveData = new MutableLiveData<>();

    private final List<Song> songs = new ArrayList<>();

    @Inject
    public SearchViewModel(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;

        List<Category> categories = songsRepository.getHomeCategories();
        for(Category category : categories) {
            songs.addAll(category.getSongs());
        }
    }

    public void searchForText(String text) {
        String textLowerCase = text.toLowerCase(Locale.ROOT);
        List<Song> resultSongs = new ArrayList<>();
        if(!"".equals(textLowerCase) && textLowerCase.length() >= 2) {
            for (Song song : songs) {
                if (song.getTitle().toLowerCase(Locale.ROOT).contains(textLowerCase) ||
                        song.getText().toLowerCase(Locale.ROOT).contains(textLowerCase) ||
                        song.getAuthor().toLowerCase(Locale.ROOT).contains(textLowerCase)) {
                    resultSongs.add(song);
                }
            }
        }
        songsLiveData.setValue(resultSongs);
    }

    public MutableLiveData<List<Song>> getSongsLiveData() {
        return songsLiveData;
    }
}
