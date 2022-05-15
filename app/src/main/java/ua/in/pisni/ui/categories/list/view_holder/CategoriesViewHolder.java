package ua.in.pisni.ui.categories.list.view_holder;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.databinding.ItemCategoryBinding;
import ua.in.pisni.ui.categories.list.CategoriesAdapter;
import ua.in.pisni.ui.categories.list.CategorySongsAdapter;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    private ItemCategoryBinding binding;

    public CategoriesViewHolder(@NonNull ItemCategoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Category category, CategoriesAdapter.CategoryListener listener,
                     Pair<Integer, Integer> pair){
        binding.title.setText(category.getTitle());

        LinearLayoutManager manager = new LinearLayoutManager(binding.getRoot().getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        CategorySongsAdapter adapter = new CategorySongsAdapter(new CategorySongsAdapter.CategorySongDiffCallback(),
                song -> {
            listener.onSongClicked(song);
        });
        binding.recycler.setLayoutManager(manager);
        binding.recycler.setAdapter(adapter);

        if(pair != null) {
            int position = pair.first;
            int offset = pair.second;
            manager.scrollToPositionWithOffset(position, offset);
        }

        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = manager.findFirstVisibleItemPosition();
                View view = manager.findViewByPosition(position);
                int offset = 0;
                if(view != null) {
                    offset = ((int) view.getX());
                }
                listener.onSongOffsetChanged(category.getId(), new Pair<>(position, offset));
            }
        });
        adapter.submitList(category.getSongs());

        binding.root.setOnClickListener((v) -> {
            listener.onCategoryClick(category);
        });
    }

}
