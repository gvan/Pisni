package ua.in.pisni.data.repository;

import android.content.Context;

import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ua.in.pisni.data.model.Categories;
import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.model.Song;

public class SongsRepositoryImpl implements SongsRepository{

    private final String HOME_JSON = "home.json";

    private Context context;

    private final ArrayList<Category> homeCategories = new ArrayList<>();

    public SongsRepositoryImpl(Context context) {
        this.context = context;
    }


    @Override
    public List<Category> getHomeCategories() {
        if(homeCategories.isEmpty()) {
            homeCategories.addAll(getItemsFromAssets(Category.class, HOME_JSON));

            for(Category category : homeCategories) {
                category.setSongs(getSongs(category.getId()));
            }
        }
        return homeCategories;
    }

    @Override
    public List<Song> getSongs(String categoryId) {
        List<Song> songs = getItemsFromAssets(Song.class, String.format("%s.json", categoryId));
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
