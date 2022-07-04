package ua.in.pisni.ui.song;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.concurrent.TimeUnit;

import ua.in.pisni.R;
import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.FragmentSongBinding;
import ua.in.pisni.ui.base.BaseFragment;
import ua.in.pisni.utils.Const;

public class SongFragment extends BaseFragment {

    private SongViewModel viewModel;
    private FragmentSongBinding binding;

    private MediaPlayer mediaPlayer;
    private Handler progressHandler;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        if(progressHandler != null) {
            progressHandler.removeCallbacksAndMessages(null);
            progressHandler = null;
        }
    }

    private void parseArguments() {
        if(getArguments() != null) {
            int songId = getArguments().getInt(Const.SONG_ID);
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

        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.onPlayClicked();
            }
        });
        binding.audioProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
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

        viewModel.getPlayAudioLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(mediaPlayer != null) {
                    if(mediaPlayer.isPlaying()) {
                        binding.audioPlayIcon.setImageResource(R.drawable.ic_play);
                        mediaPlayer.pause();
                    } else {
                        binding.audioPlayIcon.setImageResource(R.drawable.ic_pause);
                        mediaPlayer.start();
                    }
                    binding.audioProgress.setMax(mediaPlayer.getDuration());

                    if(progressHandler == null) {
                        progressHandler = new Handler();
                        progressHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int progress = mediaPlayer.getCurrentPosition();
                                if(progress > mediaPlayer.getDuration()) {
                                    progress = mediaPlayer.getDuration();
                                }
                                binding.audioProgress.setProgress(progress);
                                binding.timeProgress.setText(getFormattedTime(progress));
                                progressHandler.postDelayed(this, 100);
                            }
                        }, 100);
                    }
                }
            }
        });

        viewModel.getShowAudioPlayerLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String audioFile) {
                binding.audioPlayer.setVisibility(View.VISIBLE);
                int identifier = getResources().getIdentifier(audioFile, "raw",
                        requireActivity().getPackageName());
                mediaPlayer = MediaPlayer.create(getContext(), identifier);

                int duration = mediaPlayer.getDuration();
                binding.timeFull.setText(getFormattedTime(duration));
                binding.timeProgress.setText(String.format("%02d:%02d", 0, 0));

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        binding.audioPlayIcon.setImageResource(R.drawable.ic_play);
                        binding.audioProgress.setProgress(0);
                        binding.timeProgress.setText(String.format("%02d:%02d", 0, 0));
                        progressHandler.removeCallbacksAndMessages(null);
                        progressHandler = null;
                    }
                });
            }
        });

    }

    private String getFormattedTime(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }


}
