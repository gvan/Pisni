package ua.in.pisni.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ua.in.pisni.R;
import ua.in.pisni.data.model.Song;
import ua.in.pisni.databinding.FragmentCategoriesBinding;
import ua.in.pisni.databinding.FragmentSearchBinding;
import ua.in.pisni.ui.base.BaseFragment;
import ua.in.pisni.ui.songs.list.SongsAdapter;
import ua.in.pisni.utils.Const;

public class SearchFragment extends BaseFragment implements TextWatcher, TextView.OnEditorActionListener {

    private SearchViewModel viewModel;
    private FragmentSearchBinding binding;
    private SongsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        setupUI();
        setupViewModel();

        return binding.getRoot();
    }

    private void setupUI(){
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            }
        });
        binding.toolbar.search.setVisibility(View.VISIBLE);
        binding.toolbar.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String text = binding.toolbar.searchInput.getText().toString();
                viewModel.searchForText(text);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.toolbar.searchInput.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager)
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(binding.toolbar.searchInput, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 100);
        binding.toolbar.searchInput.addTextChangedListener(this);
        binding.toolbar.searchInput.setOnEditorActionListener(this);

        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SongsAdapter(new SongsAdapter.SongDiffUtil(), new SongsAdapter.SongListener() {
            @Override
            public void onSongClicked(Song song) {
                hideKeyboard();
                Bundle bundle = new Bundle();
                bundle.putInt(Const.SONG_ID, song.getId());
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_searchFragment_to_songFragment, bundle);
            }
        });
        binding.recycler.setAdapter(adapter);

        binding.srcLink.link.setText(R.string.source_link);
        binding.srcLink.link.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setupViewModel(){
        viewModel.getSongsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                adapter.submitList(songs);
            }
        });
    }

    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager)
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.toolbar.searchInput.getWindowToken(), 0);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        viewModel.searchForText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard();
            String text = binding.toolbar.searchInput.getText().toString();
            viewModel.searchForText(text);
        }
        return false;
    }
}
