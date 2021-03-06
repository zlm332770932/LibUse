package com.lib.use;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.android.base.adapter.BaseRecyclerViewAdapter;
import com.lib.use.adapter.RAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Create by limin on 2021/5/18.
 **/
public class RecylerViewActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private List<String> d = Arrays.asList(
            "A","B","C","D","E","F","G"
            ,"H","I","J","K","L","M","N"
            ,"O","P","Q","R","S","T"
            ,"U","V","W","X","Y","Z");
    private RecyclerView rv ;
    private Ap<String> ap;
    private List<String> datas;
    private EditText edAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview);
        initData();
        rv = findViewById(R.id.rv);
        edAdd =findViewById(R.id.et_add);
        rv.setLayoutManager(new GridLayoutManager(this,3));
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        ap = new Ap(this, datas);
//        rv.setAdapter(ap);
        RAdapter adapter = new RAdapter(this, datas, false);
        rv.setAdapter(adapter);
        helper.attachToRecyclerView(rv);
        findViewById(R.id.tv).setOnClickListener(this);
        findViewById(R.id.tv_add).setOnClickListener(this);
    }

    private void initData() {
        datas = new ArrayList<>();
//        ?????????d????????????????????????Arrays.asList???????????????????????????????????????????????????
        for (int i = 0; i < d.size(); i++) {
            datas.add(d.get(i));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv:
                for (int i = 0; i < datas.size(); i++) {
                    Log.i(TAG, "onClick: ____"+datas.get(i));
                }
                break;
            case R.id.tv_add:
                ap.add(edAdd.getText().toString().trim());
                edAdd.setText(null);
                break;
        }
    }


    class Ap<T> extends RecyclerView.Adapter<Ap.Vh>{
        private Context context;
        public List<T> stringList;
        public Ap(Context context, List<T> stringList) {
            this.context = context;
            this.stringList = stringList;
        }

        @Override
        public Ap.Vh onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Vh(LayoutInflater.from(context).inflate(R.layout.item,null));
        }

        @Override
        public void onBindViewHolder(Ap.Vh holder, final int position) {
            holder.tv.setText(stringList.get(position).toString());
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }

        public void add(T item){
            int position = stringList.size();
            stringList.add(item);
            notifyItemInserted(position);
        }

        public void add(int position,T item){
            stringList.add(position,item);
            notifyItemInserted(position);
        }
//        public void remove(T item) {
//            final int position = stringList.indexOf(item);
//            if (-1 == position)
//                return;
//            stringList.remove(item);
//            notifyItemRemoved(position);
//        }

        public void remove(int position) {
            stringList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,stringList.size());
        }

        class Vh extends RecyclerView.ViewHolder {

            public Vh(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                iv = itemView.findViewById(R.id.iv_delete);
            }
            public TextView tv;
            public ImageView iv;
        }
    }


    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFrlg = 0;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
                dragFrlg = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                dragFrlg = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFrlg,0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //????????????  ??????????????????????????????????????????????????????????????????
//            Collections.swap(datas,viewHolder.getAdapterPosition(),target.getAdapterPosition());
//            ap.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());

            //??????????????????viewHolder???Position
            int fromPosition = viewHolder.getAdapterPosition();
            //????????????????????????item???viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(datas, i, i + 1);
                    Log.d(TAG, "onMove: from " + i + " to " + (i+1));
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(datas, i, i - 1);
                    Log.d(TAG, "onMove: from " + i + " to " + (i-1));
                }
            }
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //???????????????????????????
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
        /**
         * ????????????Item?????????????????????
         * ????????????
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.RED);
                //????????????????????????//??????70??????
                Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(70);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * ?????????????????????????????????
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
            recyclerView.getAdapter().notifyDataSetChanged();  //?????????????????????????????????????????????????????????????????????
        }
    });
}
