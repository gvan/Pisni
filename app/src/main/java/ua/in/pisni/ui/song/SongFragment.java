package ua.in.pisni.ui.song;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ua.in.pisni.R;
import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.FragmentSongBinding;
import ua.in.pisni.ui.base.BaseFragment;
import ua.in.pisni.utils.Const;

public class SongFragment extends BaseFragment {

    private SongViewModel viewModel;
    private FragmentSongBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(SongViewModel.class);
        parseArguments();
        viewModel.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSongBinding.inflate(inflater, container, false);

        setupUI();
        setupViewModel();
        return binding.getRoot();
    }

    private void parseArguments() {
        if(getArguments() != null) {
            int songId = getArguments().getInt(Const.SONG_ID);
            Log.d("MyCustomLog", String.format("songId %s", songId));
            viewModel.setSongId(songId);
        }
    }

    private void setupUI(){
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            }
        });

        binding.toolbar.share.setVisibility(View.VISIBLE);
        binding.toolbar.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.onShareClicked();
            }
        });
        binding.toolbar.favorite.setVisibility(View.VISIBLE);
        binding.toolbar.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.onFavoriteClicked();
            }
        });

        binding.srcLink.link.setText(R.string.source_link);
        binding.srcLink.link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupViewModel(){

        viewModel.getSongLiveData().observe(getViewLifecycleOwner(), new Observer<Song>() {
            @Override
            public void onChanged(Song song) {
                binding.toolbar.title.setText(song.getTitle());
                binding.song.setText(song.getText());

                String author = song.getAuthor();
                if(author != null && !author.isEmpty()) {
                    binding.author.setVisibility(View.VISIBLE);
                    binding.author.setText(String.format("%s: %s", getString(R.string.author), author));
                } else {
                    binding.author.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getShareLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, s);
                startActivity(Intent.createChooser(intent, getString(R.string.share_song)));
            }
        });

        viewModel.getFavoriteLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean favorite) {
                if(favorite) {
                    binding.toolbar.favoriteIcon.setImageResource(R.drawable.ic_star_filled);
                } else {
                    binding.toolbar.favoriteIcon.setImageResource(R.drawable.ic_star_empty);
                }
            }
        });

    }

}
