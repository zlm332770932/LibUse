package com.lib.use.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.lib.android.base.adapter.BaseRecyclerViewAdapter;
import com.lib.use.databinding.ItemBinding;

import java.util.List;

/**
 * Create by limin on 2021/5/18.
 **/
public class RAdapter extends BaseRecyclerViewAdapter<String> {

    public RAdapter(Context context, List<String> list, boolean isSizeCrrect) {
        super(context, list, isSizeCrrect);
    }

    @Override
    protected int getListSize(int size) {
        return mList.size();
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    @Override
    protected ViewBinding getViewBinding(int viewType, LayoutInflater from, ViewGroup parent) {
        return ItemBinding.inflate(from,parent,false);
    }

    @Override
    protected void convert(ViewBinding viewBinding, String entity, int position) {
        ((ItemBinding)viewBinding).tv.setText(entity);
    }
}
