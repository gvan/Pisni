package ua.in.pisni.ui.categories.list.view_holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.databinding.ItemCategoryBinding;
import ua.in.pisni.ui.categories.list.CategoriesAdapter;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    private ItemCategoryBinding binding;

    public CategoriesViewHolder(@NonNull ItemCategoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Category category, CategoriesAdapter.CategoryListener listener){
        binding.title.setText(category.getTitle());

        binding.root.setOnClickListener((v) -> {
            listener.onCategoryClick(category);
        });
    }

}
