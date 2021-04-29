package com.lib.android.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by limin on 2021/4/29.
 *
 * RecyclerView.Adapter 简单封装
 *
 * 单布局、多布局 复用ViewHolder
 *
 **/
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder> {
    protected List<T> mList = new ArrayList<T>();
    protected Context mContext;
    protected boolean mIsSizeCrrect;

    public BaseRecyclerViewAdapter(Context context, List<T> list, boolean isSizeCrrect){
        mContext = context;
        mList = list;
        mIsSizeCrrect = isSizeCrrect;
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getViewBinding(viewType, LayoutInflater.from(mContext), parent));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        convert(holder.v, mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mIsSizeCrrect)
            return mList.size();
        else
            return getListSize(mList.size());
    }

    /**
     * 这里返回的是 实际要显示条目大小 不等于 mList.size()
     *
     * 比如:列表的末尾要显示一个添加图片的按钮布局,就要用到这个抽象方法,来给适配器实际的列表大小
     *
     * @param size mList.size()
     * @return
     */
    protected abstract int getListSize(int size);

    /**
     *  这个多布局判断抽象方法
     * @param position
     * @return
     */
    protected abstract int getViewType(int position);

    /**
     * 获取 ViewBinding 的实现类
     *
     * @param viewType
     * @param from
     * @param parent
     * @return
     */
    protected abstract ViewBinding getViewBinding(int viewType, LayoutInflater from, ViewGroup parent);

    /**
     * 给ViewBinding设置数据，点击事件操作
     *
     * @param viewBinding
     * @param entity
     */
    protected abstract void convert(ViewBinding viewBinding, T entity, int position);

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewBinding v;
        ViewHolder(ViewBinding viewBinding) {
            super(viewBinding.getRoot());
            v= viewBinding;
        }
    }
}
