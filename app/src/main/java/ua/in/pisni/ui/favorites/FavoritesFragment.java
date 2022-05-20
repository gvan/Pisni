package ua.in.pisni.ui.favorites;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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

public class FavoritesFragment extends BaseFragment {

    private FavoritesViewModel viewModel;
    private FragmentCategoriesBinding binding;
    private SongsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        setupUI();
        setupViewModel();
        viewModel.initData();

        return binding.getRoot();
    }

    private void setupUI(){
        binding.toolbar.back.setVisibility(View.GONE);
        binding.toolbar.actions.setVisibility(View.GONE);
        binding.toolbar.title.setText(R.string.favorites);
        binding.toolbar.title.setPadding(getResources().getDimensionPixelOffset(R.dimen.size_12), 0, 0, 0);

        binding.bottomBar.setFavoritesSelected();

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SongsAdapter(new SongsAdapter.SongDiffUtil(), new SongsAdapter.SongListener() {
            @Override
            public void onSongClicked(Song song) {
                Bundle bundle = new Bundle();
                bundle.putInt(Const.SONG_ID, song.getId());
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_favoritesFragment_to_songFragment, bundle);
            }
        });
        binding.recycler.setAdapter(adapter);

        binding.srcLink.link.setText(R.string.source_link);
        binding.srcLink.link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupViewModel(){
        viewModel.getSongsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                adapter.submitList(songs);
            }
        });
    }

}
