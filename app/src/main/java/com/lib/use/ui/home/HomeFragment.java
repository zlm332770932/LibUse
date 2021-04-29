package com.lib.use.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lib.use.R;
import com.lib.use.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textHome.setText(s);
            }
        });

        binding.signal0.setOnClickListener(view -> binding.imageView.getDrawable().setLevel(105));
        binding.signal1.setOnClickListener(view -> binding.imageView.getDrawable().setLevel(90));
        binding.signal2.setOnClickListener(view -> binding.imageView.getDrawable().setLevel(80));
        binding.signal3.setOnClickListener(view -> binding.imageView.getDrawable().setLevel(60));
        binding.signal4.setOnClickListener(view -> binding.imageView.getDrawable().setLevel(1));
        return binding.getRoot();
    }
}