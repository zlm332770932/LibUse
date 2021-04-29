package com.lib.android.base.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;

import com.lib.android.base.R;
import com.lib.android.base.adapter.FileSelectAdapter;
import com.lib.android.base.databinding.ActivityFileSelectBinding;
import com.lib.android.base.listener.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileSelectActivity extends AppCompatActivity {

    private static final String DEFAULT_FILE_SUFFIX = ".bin";
    private static final int SORT_BY_NAME = 0x00;
    private static final int SORT_BY_TIME = 0x01;
    private static final int SORT_BY_TYPE = 0x02;
    private static final int SORT_BY_SIZE = 0x03;

    public static final String KEY_RESULT  = "com.lib.android.base.file.SELECT_RESULT";
    public static final String KEY_SUFFIX = "com.lib.android.base.file.selector.suffix";
    public static final String KEY_DIR_PATH   = "com.lib.android.base.file.KEY_DIR_PATH";

    private ActivityFileSelectBinding binding;

    private List<File> mFiles = new ArrayList<>();
    private File mCurrentDir;

    private FileSelectAdapter mAdapter;

    private int sortType = SORT_BY_NAME;
    private String fileSuffix = DEFAULT_FILE_SUFFIX;
    private String fileDir;

    /*
            "Sort By Type",
            "Sort By Size"
     */
    private static final String[] SORTS = {"By Name", "By Time",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.setTitle(R.string.base_file_selector);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        binding.toolbar.inflateMenu(R.menu.base_menu_file_select);
        binding.toolbar.setOnMenuItemClickListener((MenuItem item) -> {
            if (item.getItemId() == R.id.file_menu_root) {
                changeToRootDir();
            } else if (item.getItemId() == R.id.file_menu_sort) {
                showSortDialog();
            }
            return false;
        });

        binding.ivParent.setOnClickListener(view -> {
            if (mCurrentDir.getParent() != null) {
                mCurrentDir = mCurrentDir.getParentFile();
                update();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_SUFFIX)) {
            fileSuffix = intent.getStringExtra(KEY_SUFFIX);
        }

        if (intent.hasExtra(KEY_DIR_PATH)) {
            fileDir = intent.getStringExtra(KEY_DIR_PATH);
        }

        initCurrentDir();

        mAdapter = new FileSelectAdapter(FileSelectActivity.this,
                mFiles, true, fileSuffix);
        mAdapter.setOnItemClickListener((View view, int pos, File file) -> {
            if (file.isDirectory()) {
                mCurrentDir = file;
                update();
            } else {
                String filePath = file.getAbsolutePath();
                Intent backIntent = new Intent();
                backIntent.putExtra(KEY_RESULT, filePath);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });
        binding.rvFiles.setLayoutManager(new LinearLayoutManager(FileSelectActivity.this));
        binding.rvFiles.setAdapter(mAdapter);

        update();

    }

    private void initCurrentDir() {
        if (fileDir == null || !new File(fileDir).exists()){
            mCurrentDir = Environment.getExternalStorageDirectory();
        } else {
            mCurrentDir = new File(fileDir);
        }
    }

    private void update(){
        binding.tvCurPath.setText(mCurrentDir.toString());
        File[] files = mCurrentDir.listFiles();
        if (files == null) {
            mFiles.clear();
        } else {
            mFiles = new ArrayList<>(Arrays.asList(files));
            // sort
            Collections.sort(mFiles, (File o1, File o2) -> {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                if (sortType == SORT_BY_NAME) {
                    return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
                } else {
                    return Long.compare(o2.lastModified(), o1.lastModified());
                }
            });

        }
        mAdapter.setData(mFiles);
    }

    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort Type");
        builder.setItems(SORTS, (DialogInterface dialog, int which) -> {
            if (which != sortType) {
                sortType = which;
                update();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void changeToRootDir() {
        mCurrentDir = Environment.getExternalStorageDirectory();
        update();
    }
}