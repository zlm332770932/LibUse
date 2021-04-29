package com.lib.android.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.lib.android.base.R;
import com.lib.android.base.databinding.BaseItemFileBinding;
import com.lib.android.base.listener.OnItemClickListener;

import java.io.File;
import java.util.List;

/**
 * Create by limin on 2021/4/29.
 **/
public class FileSelectAdapter extends BaseRecyclerViewAdapter<File>{

    private OnItemClickListener<File> onItemClickListener;
    private String mTargetSuffix;

    public FileSelectAdapter(Context context, List<File> list, boolean isCrrect, String targetSuffix){
        super(context, list, isCrrect);
        mTargetSuffix = targetSuffix;
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
        return BaseItemFileBinding.inflate(from, parent, false);
    }

    @Override
    protected void convert(ViewBinding viewBinding, File entity, int position) {
        if (entity.isDirectory()){
            ((BaseItemFileBinding) viewBinding).ivIcon.setImageResource(R.drawable.ic_folder);
            ((BaseItemFileBinding) viewBinding).ivRight.setVisibility(View.VISIBLE);
        } else {
            ((BaseItemFileBinding) viewBinding).ivRight.setVisibility(View.GONE);
            if (mTargetSuffix != null &&
                    !mTargetSuffix.equals("") &&
                    entity.getName().endsWith(mTargetSuffix)) {
                ((BaseItemFileBinding) viewBinding).ivIcon.setImageResource(R.drawable.ic_file_checked);
            }else {
                ((BaseItemFileBinding) viewBinding).ivIcon.setImageResource(R.drawable.ic_file_unchecked);
            }
        }
        ((BaseItemFileBinding) viewBinding).tvName.setText(entity.getName());

        viewBinding.getRoot().setOnClickListener(view -> {
            if (onItemClickListener != null)
                onItemClickListener.onClick(viewBinding.getRoot(), position, entity);
        });

    }

    public void setData(List<File> files){
        this.mList = files;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<File> listener){
        onItemClickListener = listener;
    }
}
