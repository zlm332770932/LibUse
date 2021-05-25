package com.lib.use.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.android.base.colorgradient.LinearGradientUtil;
import com.lib.use.R;
import com.lib.use.data.Bean;

import java.util.List;

/**
 * Create by limin on 2021/5/25.
 **/
public class SpAdapter2 extends BaseAdapter {
    private Context context;
    private List<Bean> beans;
    public SpAdapter2(@NonNull Context context, @NonNull List<Bean> objects) {
        this.context = context;
        this.beans = objects;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpHoler holer = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sp, null);
            holer = new SpHoler();
            holer.textView = convertView.findViewById(R.id.tv);
            convertView.setTag(holer);
        }else {
            holer = (SpHoler) convertView.getTag();
        }
        holer.textView.setText(beans.get(position).name + "(" +beans.get(position).id+")");

        return convertView;
    }

    public class SpHoler{
        public TextView textView;
    }
}
