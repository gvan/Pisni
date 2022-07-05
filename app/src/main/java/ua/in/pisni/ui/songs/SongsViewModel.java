package ua.in.pisni.ui.songs;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.nio.Buffer;
import java.util.List;

import javax.inject.Inject;

import ua.in.pisni.data.model.Song;
import ua.in.pisni.data.repository.SongsRepository;

public class SongsViewModel extends ViewModel {

    private SongsRepository songsRepository;
    private String chapterId = null;
    private String categoryId = null;

    private MutableLiveData<String> chapterIdLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Song>> songsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> titleLiveData = new MutableLiveData<>();

    @Inject
    public SongsViewModel(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public void init() {
        songsLiveData.setValue(songsRepository.getSongs(categoryId));
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
        chapterIdLiveData.setValue(chapterId);
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        titleLiveData.setValue(title);
    }

    public MutableLiveData<String> getChapterIdLiveData() {
        return chapterIdLiveData;
    }

    public MutableLiveData<List<Song>> getSongsLiveData() {
        return songsLiveData;
    }

    public MutableLiveData<String> getTitleLiveData() {
        return titleLiveData;
    }
}
