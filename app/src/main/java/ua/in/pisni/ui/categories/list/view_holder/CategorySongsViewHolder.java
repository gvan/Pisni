package ua.in.pisni.ui.categories.list.view_holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.ItemCategorySongBinding;
import ua.in.pisni.ui.categories.list.CategorySongsAdapter;
import ua.in.pisni.utils.Utils;

public class CategorySongsViewHolder extends RecyclerView.ViewHolder {

    private ItemCategorySongBinding binding;

    public CategorySongsViewHolder(@NonNull ItemCategorySongBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Song song, CategorySongsAdapter.CategorySongListener listener) {
        binding.title.setText(song.getTitle());
        binding.content.setText(Utils.getFirstTwoRows(song.getText()));

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSongClicked(song);
            }
        });
    }

}
