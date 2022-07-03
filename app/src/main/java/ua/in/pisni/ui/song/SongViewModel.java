package ua.in.pisni.ui.song;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import ua.in.pisni.data.model.Song;
import ua.in.pisni.data.repository.SongsRepository;
import ua.in.pisni.data.source.preferences.SongsPreferences;
import ua.in.pisni.utils.SingleLiveEvent;

public class SongViewModel extends ViewModel {

    private final MutableLiveData<Song> songLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> shareLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> favoriteLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> showPlayAudioLiveData = new SingleLiveEvent<>();
    private final MutableLiveData<String> playAudioLiveData = new SingleLiveEvent<>();

    private SongsRepository songsRepository;
    private SongsPreferences songsPreferences;
    private int songId;

    @Inject
    public SongViewModel(SongsRepository songsRepository, SongsPreferences songsPreferences) {
        this.songsRepository = songsRepository;
        this.songsPreferences = songsPreferences;
    }

    public void init(){
        Song song = songsRepository.getSong(songId);
        songLiveData.setValue(song);
        favoriteLiveData.setValue(songsPreferences.isFavoriteSong(songId));

        if(!TextUtils.isEmpty(song.getAudio_file_name())) {
            showPlayAudioLiveData.setValue(true);
        }
    }

    public void onShareClicked(){
        Song song = songsRepository.getSong(songId);
        String title = song.getTitle();
        String songText = song.getText();
        String author = song.getAuthor();

        String sharedText = String.format("%s\n\n%s", title, songText);
        if(author != null && !"".equals(author)) {
            sharedText = String.format("%s\n\nАвтор: %s", sharedText, author);
        }
        shareLiveData.setValue(sharedText);
    }

    public void onFavoriteClicked(){
        if(songsPreferences.isFavoriteSong(songId)) {
            songsPreferences.removeFavoriteSong(songId);
            favoriteLiveData.setValue(false);
        } else {
            songsPreferences.addFavoriteSong(songId);
            favoriteLiveData.setValue(true);
        }
    }

    public void onPlayClicked() {
        Song song = songsRepository.getSong(songId);
        if(song.getAudio_file_name() != null) {
            playAudioLiveData.setValue(song.getAudio_file_name());
        }
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public MutableLiveData<Song> getSongLiveData() {
        return songLiveData;
    }

    public MutableLiveData<String> getShareLiveData() {
        return shareLiveData;
    }

    public MutableLiveData<Boolean> getFavoriteLiveData() {
        return favoriteLiveData;
    }

    public MutableLiveData<String> getPlayAudioLiveData() {
        return playAudioLiveData;
    }

    public MutableLiveData<Boolean> getShowPlayAudioLiveData() {
        return showPlayAudioLiveData;
    }
}
