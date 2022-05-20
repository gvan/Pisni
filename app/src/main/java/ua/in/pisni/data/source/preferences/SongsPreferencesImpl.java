package ua.in.pisni.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ua.in.pisni.utils.Const;

public class SongsPreferencesImpl implements SongsPreferences{

    private final Context context;

    public SongsPreferencesImpl(Context context) {
        this.context = context;
    }

    @Override
    public void addFavoriteSong(int songId) {
        SharedPreferences preferences = context.getSharedPreferences
                (Const.SONGS_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> ids = new HashSet<>(preferences.getStringSet(Const.FAVORITE_SONGS, new HashSet<>()));
        ids.add(String.valueOf(songId));
        preferences.edit().putStringSet(Const.FAVORITE_SONGS, ids).apply();

    }

    @Override
    public void removeFavoriteSong(int songId) {
        SharedPreferences preferences = context.getSharedPreferences
                (Const.SONGS_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> ids = new HashSet<>(preferences.getStringSet(Const.FAVORITE_SONGS, new HashSet<>()));
        ids.remove(String.valueOf(songId));
        preferences.edit().putStringSet(Const.FAVORITE_SONGS, ids).apply();
    }

    @Override
    public boolean isFavoriteSong(int songId) {
        SharedPreferences preferences = context.getSharedPreferences
                (Const.SONGS_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> ids = preferences.getStringSet(Const.FAVORITE_SONGS, new HashSet<>());
        return ids.contains(String.valueOf(songId));
    }

    @Override
    public List<Integer> getFavoriteSongs() {
        SharedPreferences preferences = context.getSharedPreferences
                (Const.SONGS_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> ids = preferences.getStringSet(Const.FAVORITE_SONGS, new HashSet<>());
        List<Integer> idsInt = new ArrayList<>();
        for(String value : ids) {
            idsInt.add(Integer.valueOf(value));
        }
        return idsInt;
    }
}
