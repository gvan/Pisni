package ua.in.pisni.ui.songs.list.view_holder;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.ItemSongBinding;
import ua.in.pisni.ui.songs.list.SongsAdapter;
import ua.in.pisni.utils.Utils;

public class SongsViewHolder extends RecyclerView.ViewHolder{

    private ItemSongBinding binding;

    public SongsViewHolder(@NonNull ItemSongBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Song song, SongsAdapter.SongListener listener) {
        binding.title.setText(song.getTitle());
        binding.songSort.setText(Utils.getFirstTwoRows(song.getText()));

        binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSongClicked(song);
            }
        });
    }



}
