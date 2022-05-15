package ua.in.pisni.ui.categories;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.repository.SongsRepository;
import ua.in.pisni.utils.SingleLiveEvent;

public class CategoriesViewModel extends ViewModel {

    private SongsRepository songsRepository;
    private final Map<String, Pair<Integer, Integer>> songsOffsets = new HashMap<>();

    private MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private MutableLiveData<Map<String, Pair<Integer, Integer>>> songsOffsetsLiveData = new SingleLiveEvent<>();

    @Inject
    public CategoriesViewModel(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public void init() {
        songsOffsetsLiveData.setValue(songsOffsets);
        categoriesLiveData.setValue(songsRepository.getHomeCategories());
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
}
