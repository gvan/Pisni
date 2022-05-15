package ua.in.pisni.ui.song;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class SongViewModel extends ViewModel {

    private final MutableLiveData<String> titleLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> songLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> authorLiveData = new MutableLiveData<>();

    @Inject
    public SongViewModel() {
    }

    public void setTitle(String title) {
        titleLiveData.setValue(title);
    }

    public void setSong(String song) {
        songLiveData.setValue(song);
    }

    public void setAuthor(String author) {
        authorLiveData.setValue(author);
    }

    public MutableLiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public MutableLiveData<String> getSongLiveData() {
        return songLiveData;
    }

    public MutableLiveData<String> getAuthorLiveData() {
        return authorLiveData;
    }
}
