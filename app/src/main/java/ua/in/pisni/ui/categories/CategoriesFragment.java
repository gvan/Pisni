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
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.Map;

import ua.in.pisni.R;
import ua.in.pisni.data.model.Category;
import ua.in.pisni.data.model.Song;
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
        binding.toolbar.back.setPadding(getResources().getDimensionPixelOffset(R.dimen.size_12), 0, 0, 0);
        binding.toolbar.backIcon.setVisibility(View.GONE);

        binding.toolbar.search.setVisibility(View.VISIBLE);
        binding.toolbar.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_categoriesFragment_to_searchFragment);
            }
        });

        binding.bottomBar.setHomeSelected();

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CategoriesAdapter(new CategoriesAdapter.CategoryDiffCallback(),
                new CategoriesAdapter.CategoryListener() {
                    @Override
                    public void onCategoryClick(Category category) {
                        openCategory(category.getId(), category.getTitle());
                    }

                    @Override
                    public void onSongClicked(Song song) {
                        openSong(song);
                    }

                    @Override
                    public void onSongOffsetChanged(String categoryId, Pair<Integer, Integer> offset) {
                        viewModel.setPoemsOffsets(categoryId, offset);
                    }
                });
        binding.recycler.setAdapter(adapter);

        binding.srcLink.link.setText(R.string.source_link);
        binding.srcLink.link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupViewModel() {

        viewModel.getSongsOffsetsLiveData().observe(getViewLifecycleOwner(), new Observer<Map<String, Pair<Integer, Integer>>>() {
            @Override
            public void onChanged(Map<String, Pair<Integer, Integer>> stringPairMap) {
                if(adapter != null) {
                    adapter.setSongsOffsets(stringPairMap);
                }
            }
        });

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

    private void openSong(Song song) {
        Bundle arguments = new Bundle();
        arguments.putInt(Const.SONG_ID, song.getId());
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_categoriesFragment_to_songFragment, arguments);
    }

}
