package ua.in.pisni.data.repository;

import java.util.List;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.model.Song;

public interface SongsRepository {

    List<Category> getHomeCategories();

    List<Category> getAuthorsCategories();

    List<Song> getSongs(String categoryId);

    Song getSong(int songId);

    List<Song> getSongsByIds(List<Integer> ids);

}
