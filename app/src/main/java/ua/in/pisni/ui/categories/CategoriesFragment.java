package ua.in.pisni.ui.categories;

import android.content.res.Resources;
import android.os.Bundle;
import android.telephony.RadioAccessSpecifier;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ua.in.pisni.R;
import ua.in.pisni.data.model.Category;
import ua.in.pisni.databinding.FragmentCategoriesBinding;
import ua.in.pisni.ui.base.BaseFragment;
import ua.in.pisni.ui.categories.list.CategoriesAdapter;
import ua.in.pisni.utils.Const;

public class CategoriesFragment extends BaseFragment {

    private CategoriesViewModel viewModel;
    private FragmentCategoriesBinding binding;
    private CategoriesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        viewModel = new ViewModelProvider(this, viewModelFactory).get(CategoriesViewModel.class);
        viewModel.init();
        setupViewModel();

        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        setupUI();

        return binding.getRoot();
    }

    private void setupUI() {
        binding.toolbar.title.setText(R.string.ukrainian_songs);
        binding.toolbar.title.setPadding(getResources().getDimensionPixelOffset(R.dimen.size_12), 0, 0, 0);
        binding.toolbar.back.setVisibility(View.GONE);

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CategoriesAdapter(new CategoriesAdapter.CategoryDiffCallback(),
                new CategoriesAdapter.CategoryListener() {
                    @Override
                    public void onCategoryClick(Category category) {
                        openCategory(category.getId(), category.getTitle());
                    }
                });
        binding.recycler.setAdapter(adapter);

        binding.srcLink.link.setText(R.string.source_link);
        binding.srcLink.link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupViewModel() {
        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.submitList(categories);
            }
        });
    }

    private void openCategory(String categoryId, String title) {
        Bundle arguments = new Bundle();
        arguments.putString(Const.CATEGORY_TYPE, categoryId);
        arguments.putString(Const.TITLE, title);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_categoriesFragment_to_songsFragment, arguments);
    }

}
