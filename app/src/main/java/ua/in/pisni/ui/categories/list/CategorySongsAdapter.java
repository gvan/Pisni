package ua.in.pisni.ui.categories.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.ItemCategorySongBinding;
import ua.in.pisni.ui.categories.list.view_holder.CategorySongsViewHolder;

public class CategorySongsAdapter extends ListAdapter<Song, CategorySongsViewHolder> {

    private CategorySongListener listener;

    public CategorySongsAdapter(@NonNull DiffUtil.ItemCallback<Song> diffCallback,
                                   CategorySongListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategorySongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategorySongBinding binding = ItemCategorySongBinding.inflate(inflater);
        return new CategorySongsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySongsViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class CategorySongDiffCallback extends DiffUtil.ItemCallback<Song> {

        @Override
        public boolean areItemsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem.getTitle() == newItem.getTitle();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem == newItem;
        }
    }

    public interface CategorySongListener {
        void onSongClicked(Song song);
    }

}
