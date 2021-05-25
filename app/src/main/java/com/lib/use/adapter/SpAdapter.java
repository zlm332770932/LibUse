package com.lib.use.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.android.base.adapter.BaseRecyclerViewAdapter;
import com.lib.use.R;
import com.lib.use.data.Bean;

import org.json.JSONObject;

/**
 * Create by limin on 2021/5/25.
 **/
public class SpAdapter extends ArrayAdapter<Bean> {
    private Context context;
    private Bean[] beans;
    public SpAdapter(@NonNull Context context, int resource, @NonNull Bean[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.beans = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = View.inflate(context,R.layout.item_sp,null);//获得Spinner布局View
        if(convertView!=null)
        {
            TextView textView = (TextView)convertView.findViewById(R.id.tv);
            textView.setText(beans[position].name + "(" +beans[position].id+")");
        }
        return convertView;
    }
}
