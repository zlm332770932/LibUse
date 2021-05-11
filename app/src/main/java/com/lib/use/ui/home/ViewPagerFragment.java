package com.lib.use.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lib.use.databinding.FragmentViewpagerBinding;

/**
 * Create by limin on 2021/5/9.
 **/
public class ViewPagerFragment extends Fragment {

    private FragmentViewpagerBinding binding;
    private String mText;
    public ViewPagerFragment(String text){
        super();
        mText = text;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentViewpagerBinding.inflate(inflater);
        binding.textView.setText(mText);
        return binding.getRoot();
    }
}
