package ua.in.pisni.ui.songs.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.ItemSongBinding;
import ua.in.pisni.ui.songs.list.view_holder.SongsViewHolder;

public class SongsAdapter extends ListAdapter<Song, SongsViewHolder> {

    private SongListener listener;

    public SongsAdapter(@NonNull DiffUtil.ItemCallback<Song> diffCallback, SongListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSongBinding binding = ItemSongBinding.inflate(inflater, parent, false);
        return new SongsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class SongDiffUtil extends DiffUtil.ItemCallback<Song> {

        @Override
        public boolean areItemsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem.getTitle() == newItem.getTitle();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem == newItem;
        }
    }

    public interface SongListener {
        void onSongClicked(Song song);
    }

}
