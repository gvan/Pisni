package ua.in.pisni.ui.categories.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ua.in.pisni.data.model.Category;
import ua.in.pisni.databinding.ItemCategoryBinding;
import ua.in.pisni.ui.categories.list.view_holder.CategoriesViewHolder;

public class CategoriesAdapter extends ListAdapter<Category, CategoriesViewHolder> {

    private CategoryListener listener;

    public CategoriesAdapter(@NonNull DiffUtil.ItemCallback<Category> diffCallback, CategoryListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater);
        return new CategoriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class CategoryDiffCallback extends DiffUtil.ItemCallback<Category> {

        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem == newItem;
        }
    }

    public interface CategoryListener {
        void onCategoryClick(Category category);
    }

}
