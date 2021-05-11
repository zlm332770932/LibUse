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
import com.lib.use.adapter.ViewPagerAdapter;
import com.lib.use.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    /** 页面list **/
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    /** 页面title list **/
    List<String>   titleList    = new ArrayList<String>();

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


        fragmentList.add(new ViewPagerFragment("页面1"));
        fragmentList.add(new ViewPagerFragment("页面2"));
        fragmentList.add(new ViewPagerFragment("页面3"));
        fragmentList.add(new ViewPagerFragment("页面4"));
        fragmentList.add(new ViewPagerFragment("页面5"));
        fragmentList.add(new ViewPagerFragment("页面6"));
        titleList.add("title 1 ");
        titleList.add("title 2 ");
        titleList.add("title 3 ");
        titleList.add("title 4 ");
        titleList.add("title 5 ");
        titleList.add("title 6 ");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        binding.viewpager.setAdapter(adapter);


        return binding.getRoot();
    }
}