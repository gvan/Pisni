package ua.in.pisni.ui.categories;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.repository.SongsRepository;
import ua.in.pisni.utils.Const;
import ua.in.pisni.utils.SingleLiveEvent;

public class CategoriesViewModel extends ViewModel {

    private SongsRepository songsRepository;
    private final Map<String, Pair<Integer, Integer>> songsOffsets = new HashMap<>();

    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Pair<Integer, Integer>>> songsOffsetsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> chapterIdLiveData = new MutableLiveData<>();

    private String chapterId = "";

    @Inject
    public CategoriesViewModel(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public void init() {
        songsOffsetsLiveData.setValue(songsOffsets);
        switch (chapterId) {
            case Const.AUTHORS_CHAPTER: {
                categoriesLiveData.setValue(songsRepository.getAuthorsCategories());
                break;
            }
            default: {
                categoriesLiveData.setValue(songsRepository.getHomeCategories());
                break;
            }
        }
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
        chapterIdLiveData.setValue(chapterId);
    }

    public void setPoemsOffsets(String categoryId, Pair<Integer, Integer> offset) {
        songsOffsets.put(categoryId, offset);
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public MutableLiveData<Map<String, Pair<Integer, Integer>>> getSongsOffsetsLiveData() {
        return songsOffsetsLiveData;
    }

    public MutableLiveData<String> getChapterIdLiveData() {
        return chapterIdLiveData;
    }
}
