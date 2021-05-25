package com.lib.use.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lib.android.base.colorgradient.LinearGradientUtil;
import com.lib.android.base.ui.FileSelectActivity;
import com.lib.use.R;
import com.lib.use.adapter.SpAdapter;
import com.lib.use.adapter.SpAdapter2;
import com.lib.use.data.Bean;
import com.lib.use.databinding.FragmentDashboardBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    // Request code for creating a PDF document.
    private static final int CREATE_FILE = 1;

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textDashboard.setText(s);
            }
        });

        binding.btnFileSelect.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), FileSelectActivity.class);
            startActivity(intent);
        });

        binding.btnCreateFile.setOnClickListener(v -> {
            createFile("share.json");
        });

        binding.btnConnected.setOnClickListener(v -> {
            binding.imageView2.setImageResource(R.drawable.ic_ble_connected);
        });

        binding.btnConnecting.setOnClickListener(v -> {
            binding.imageView2.setImageResource(R.drawable.ic_ble_connecting);
            AnimationDrawable animationDrawable1 = (AnimationDrawable) binding.imageView2.getDrawable();
            animationDrawable1.start();
        });

        binding.btnDisconnect.setOnClickListener(v -> {
            binding.imageView2.setImageResource(R.drawable.ic_ble_disconnected);
        });

        //设置Tint
        binding.button5.setOnClickListener(v -> {
            binding.navBtn.setTint(R.color.blue);
        });

        binding.button6.setOnClickListener(v -> {
            binding.navBtn.setText("我的");
        });

        binding.button7.setOnClickListener(v -> {
            binding.navBtn.setImgResource(R.drawable.right_arrow);
        });

        // #FFA726
        int startColor = Color.rgb(0xFF, 0xA7, 0x26);

        // #4FC3F7
        int endColor = Color.rgb(0x4F, 0xC3, 0xF7);

        LinearGradientUtil linearGradientUtil = new LinearGradientUtil(startColor, endColor);
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                seekBar.getProgressDrawable().setTint(Color.rgb(255,255,progress));
                Log.d(TAG, "onProgressChanged: fromuser = " + fromUser);
                float radio = (float) (progress * 1.0 / 255);
                Log.d(TAG, "onProgressChanged: radio = " + radio);
                int color = linearGradientUtil.getColor(radio);
                Log.d(TAG, "onProgressChanged: color = " + color);
                seekBar.getThumb().setTint(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: " + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: " + seekBar.getProgress());
            }
        });

        LinearGradientUtil linearGradientUtil2 = new LinearGradientUtil(
                getResources().getColor(R.color.dim_start_color,null),
                getResources().getColor(R.color.dim_end_color, null));
        binding.seekbarDim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.getThumb().setTint(linearGradientUtil2.getColor((float) (progress * 1.0 / 255)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.button8.setOnClickListener(v -> {
            int progress = binding.seekbar.getProgress();
            binding.seekbar.setProgress((progress + 10) % binding.seekbar.getMax());
        });

        ArrayList<Bean> sps = new ArrayList<>();
        for (int i = 0;i < 10 ;i++){
            sps.add(new Bean("bean " + i, i));
        }
//        SpAdapter spAdapter = new SpAdapter(requireActivity(), R.layout.item_sp, sps.toArray(new Bean[sps.size()]));
//        binding.spinner.setAdapter(spAdapter);
        SpAdapter2 spAdapter2 = new SpAdapter2(requireActivity(), sps);
        binding.spinner.setAdapter(spAdapter2);

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FILE){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                Log.e(TAG, "create file: "+ uri.getPath() );
//                File file = new File(uri.getPath());
                alterDocument(uri);
            }
        }
    }

    private void createFile(String name){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
//        intent.setDataAndType(Uri.fromFile(Environment.getExternalStorageDirectory()), "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/json");
        intent.putExtra(Intent.EXTRA_TITLE, name);

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
//            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, CREATE_FILE);
    }

    private void alterDocument(Uri uri) {
        try {
            ParcelFileDescriptor pfd = getActivity().getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(("Overwritten at " + System.currentTimeMillis() +
                    "\n").getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}