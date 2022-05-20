package ua.in.pisni.ui.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import ua.in.pisni.R;
import ua.in.pisni.databinding.MenuBottomBarBinding;

public class BottomNavigationBar extends FrameLayout {

    private MenuBottomBarBinding binding;

    public BottomNavigationBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = MenuBottomBarBinding.inflate(inflater, this, true);
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        if(context instanceof Activity) {
            final Activity activity = (Activity) context;
            binding.menuHome.setOnClickListener(v -> {
                Navigation.findNavController(activity, R.id.nav_host_fragment)
                        .navigate(R.id.action_to_homeFragment);
            });

            binding.menuFavorites.setOnClickListener(v -> {
                Navigation.findNavController(activity, R.id.nav_host_fragment)
                        .navigate(R.id.action_to_favoritesFragment);
            });
        }
    }

    public void setHomeSelected() {
        resetBottomBar();

        binding.menuHomeIcon.setImageResource(R.drawable.ic_home_filled);
        binding.menuHomeIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);
    }

    public void setFavoritesSelected() {
        resetBottomBar();

        binding.menuFavoritesIcon.setImageResource(R.drawable.ic_star_filled);
        binding.menuFavoritesIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent),
                PorterDuff.Mode.SRC_IN);
    }

    public void resetBottomBar() {
        binding.menuHomeIcon.setImageResource(R.drawable.ic_home_empty);
        binding.menuHomeIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_blue),
                PorterDuff.Mode.SRC_IN);

        binding.menuFavoritesIcon.setImageResource(R.drawable.ic_star_empty);
        binding.menuFavoritesIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.gray_blue),
                PorterDuff.Mode.SRC_IN);
    }

}
