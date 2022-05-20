package ua.in.pisni.data.source.preferences;

import java.util.List;

public interface SongsPreferences {

    void addFavoriteSong(int songId);

    void removeFavoriteSong(int songId);

    boolean isFavoriteSong(int songId);

    List<Integer> getFavoriteSongs();

}
