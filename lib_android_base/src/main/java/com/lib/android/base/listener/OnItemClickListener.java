package com.lib.android.base.listener;

import android.view.View;

/**
 * Create by limin on 2020/9/4.
 **/
public interface OnItemClickListener<T> {
    void onClick(View view, int pos, T t);
}
