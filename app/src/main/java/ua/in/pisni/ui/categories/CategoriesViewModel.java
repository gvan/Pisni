package ua.in.pisni.ui.categories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.repository.SongsRepository;

public class CategoriesViewModel extends ViewModel {

    private SongsRepository songsRepository;

    private MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();

    @Inject
    public CategoriesViewModel(SongsRepository songsRepository) {
        this.songsRepository = songsRepository;
    }

    public void init() {
        categoriesLiveData.setValue(songsRepository.getHomeCategories());
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

}
