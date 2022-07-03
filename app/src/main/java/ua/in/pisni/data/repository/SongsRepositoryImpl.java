package ua.in.pisni.data.repository;

import android.content.Context;

import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.in.pisni.data.model.Categories;
import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.model.Song;

public class SongsRepositoryImpl implements SongsRepository{

    private final String HOME_JSON = "home.json";
    private final String AUTHORS_JSON = "authors.json";

    private Context context;

    private final ArrayList<Category> homeCategories = new ArrayList<>();
    private final ArrayList<Category> authorsCategories = new ArrayList<>();

    public SongsRepositoryImpl(Context context) {
        this.context = context;
        initHomeCategories();
        initAuthorsCategories();
    }


    @Override
    public List<Category> getHomeCategories() {
        initHomeCategories();
        return homeCategories;
    }

    private void initHomeCategories() {
        if(homeCategories.isEmpty()) {
            homeCategories.addAll(getItemsFromAssets(Category.class, HOME_JSON));

            for(Category category : homeCategories) {
                category.setSongs(getSongs(category.getId()));
            }
        }
    }

    @Override
    public List<Category> getAuthorsCategories() {
        initAuthorsCategories();
        return authorsCategories;
    }

    private void initAuthorsCategories() {
        if(authorsCategories.isEmpty()) {
            authorsCategories.addAll(getItemsFromAssets(Category.class, AUTHORS_JSON));

            for(Category category : authorsCategories) {
                category.setSongs(getSongs(category.getId()));
            }
        }
    }


    @Override
    public List<Song> getSongs(String categoryId) {
        List<Song> songs = null;
        for(Category category : homeCategories) {
            if(category.getId() == categoryId) {
                songs = category.getSongs();
            }
        }
        for(Category category : authorsCategories) {
            if(category.getId() == categoryId) {
                songs = category.getSongs();
            }
        }
        if(songs == null) {
            songs = getItemsFromAssets(Song.class, String.format("%s.json", categoryId));
        }
        return songs;
    }

    @Override
    public Song getSong(int songId) {
        for(Category category : homeCategories) {
            for(Song song : category.getSongs()) {
                if(song.getId() == songId) {
                    return song;
                }
            }
        }
        for(Category category : authorsCategories) {
            for(Song song : category.getSongs()) {
                if(song.getId() == songId) {
                    return song;
                }
            }
        }
        return null;
    }

    @Override
    public List<Song> getSongsByIds(List<Integer> ids) {
        List<Song> songs = new ArrayList<>();
        for(Category category : homeCategories) {
            for(Song song : category.getSongs()) {
                for(Integer id : ids) {
                    if(song.getId() == id) {
                        songs.add(song);
                    }
                }
            }
        }

        for(Category category : authorsCategories) {
            for(Song song : category.getSongs()) {
                for(Integer id : ids) {
                    if(song.getId() == id) {
                        songs.add(song);
                    }
                }
            }
        }
        return songs;
    }

    private <T> List<T> getItemsFromAssets(Class<T> type, String fileName) {
        List<T> items = new ArrayList<>();
        String jsonStr = "[]";
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonStr = new String(buffer, "UTF-8");
            items.addAll(JSON.std.listOfFrom(type, jsonStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

}
