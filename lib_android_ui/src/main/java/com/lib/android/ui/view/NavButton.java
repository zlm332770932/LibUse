package com.lib.android.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.lib.android.ui.R;
import com.lib.android.ui.databinding.NavButtonBinding;

/**
 * Create by limin on 2021/5/17.
 **/
public class NavButton extends LinearLayout {
    private NavButtonBinding binding;

    public NavButton(Context context) {
        super(context);
        initViews(context);
    }

    public NavButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public NavButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public NavButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.nav_button, this);
        binding = NavButtonBinding.bind(view);
    }

    public void setImgResource(int res){
        binding.ivNav.setImageResource(res);
    }

    public void setText(String navName){
        binding.tvNav.setText(navName);
    }

    public void setTint(int colorRes){
        ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), colorRes));
        binding.ivNav.setImageTintList(colorStateList);
        binding.tvNav.setTextColor(colorStateList);
    }
}
