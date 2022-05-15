package ua.in.pisni.ui.song;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ua.in.pisni.utils.SingleLiveEvent;

public class SongViewModel extends ViewModel {

    private final MutableLiveData<String> titleLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> songLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> authorLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> shareLiveData = new SingleLiveEvent<>();

    @Inject
    public SongViewModel() {
    }

    public void onShareClicked(){
        String title = titleLiveData.getValue();
        String song = songLiveData.getValue();
        String author = authorLiveData.getValue();

        String sharedText = String.format("%s\n\n%s", title, song);
        if(author != null && !"".equals(author)) {
            sharedText = String.format("%s\n\nАвтор: %s", sharedText, author);
        }
        shareLiveData.setValue(sharedText);
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

    public MutableLiveData<String> getShareLiveData() {
        return shareLiveData;
    }
}
