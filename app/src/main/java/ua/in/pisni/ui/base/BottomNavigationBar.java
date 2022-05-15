package ua.in.pisni.ui.base;

import android.app.Activity;
import android.content.Context;
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
        }
    }

}
