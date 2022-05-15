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

public class SongsViewHolder extends RecyclerView.ViewHolder{

    private ItemSongBinding binding;

    public SongsViewHolder(@NonNull ItemSongBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Song song, SongsAdapter.SongListener listener) {
        binding.title.setText(song.getTitle());
        binding.songSort.setText(getFirstTwoRows(song.getText()));

        binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSongClicked(song);
            }
        });
    }

    private String getFirstTwoRows(String song) {
        String[] lines = song.split("\\n");
        List<String> rows = new ArrayList<>();
        for(int i = 0;i < lines.length;i++) {
            String line = lines[i];
            if("".equals(line)) {
                continue;
            }
            boolean latinLetter = false;
            for(int j = 0;j < line.length();j++) {
                char c = line.charAt(j);
                if((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                    latinLetter = true;
                }
            }
            if(latinLetter) continue;

            rows.add(line);
            if(rows.size() == 2) {
                break;
            }
        }

        StringBuilder rowsBuilder = new StringBuilder();
        for(int i = 0;i < rows.size();i++) {
            if(i != 0) {
                rowsBuilder.append("\n");
            }
            rowsBuilder.append(rows.get(i));
        }
        return rowsBuilder.toString();
    }

}
