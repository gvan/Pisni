package ua.in.pisni.ui.songs;

import android.os.Bundle;
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
import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.FragmentCategoriesBinding;
import ua.in.pisni.ui.base.BaseFragment;
import ua.in.pisni.ui.songs.list.SongsAdapter;
import ua.in.pisni.utils.Const;

public class SongsFragment extends BaseFragment {

    private SongsViewModel viewModel;
    private FragmentCategoriesBinding binding;
    private SongsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(SongsViewModel.class);
        parseArguments();
        viewModel.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        setupUI();
        setupViewModel();

        return binding.getRoot();
    }

    private void parseArguments() {
        if(getArguments() != null) {
            String chapterId = getArguments().getString(Const.CHAPTER_ID, "");
            String categoryId = getArguments().getString(Const.CATEGORY_ID, "");
            String title = getArguments().getString(Const.TITLE, "");
            viewModel.setChapterId(chapterId);
            viewModel.setCategoryId(categoryId);
            viewModel.setTitle(title);
        }
    }

    private void setupUI(){
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            }
        });
        binding.bottomBar.setHomeSelected();

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SongsAdapter(new SongsAdapter.SongDiffUtil(), new SongsAdapter.SongListener() {
            @Override
            public void onSongClicked(Song song) {
                openSong(song.getId());
            }
        });
        binding.recycler.setAdapter(adapter);

        binding.srcLink.link.setText(R.string.source_link);
        binding.srcLink.link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupViewModel(){

        viewModel.getChapterIdLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case Const.CATEGORIES_CHAPTER: {
                        binding.bottomBar.setHomeSelected();
                        break;
                    }
                    case Const.AUTHORS_CHAPTER:{
                        binding.bottomBar.setAuthorsSelected();
                        break;
                    }
                }
            }
        });

        viewModel.getSongsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                adapter.submitList(songs);
            }
        });

        viewModel.getTitleLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.toolbar.title.setText(s);
            }
        });
    }

    private void openSong(int songId) {
        Bundle arguments = new Bundle();
        arguments.putInt(Const.SONG_ID, songId);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_songsFragment_to_songFragment, arguments);
    }

}
