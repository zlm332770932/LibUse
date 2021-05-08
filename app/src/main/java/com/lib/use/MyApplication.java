package com.lib.use;

import android.app.Application;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.lib.android.base.tool.CrashHandler;

import java.util.Arrays;

import static android.os.Environment.DIRECTORY_MUSIC;

/**
 * Create by limin on 2021/4/30.
 **/
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext(),"crash-test");

        Log.d(TAG, "getExternalCacheDir() : " + getExternalCacheDir().getAbsolutePath());
        Log.d(TAG, "getExternalCacheDirs() : " + Arrays.toString(getExternalCacheDirs()));
        Log.d(TAG, "getExternalFilesDir(NULL) : " + getExternalFilesDir(null).getAbsolutePath());
        Log.d(TAG, "getExternalFilesDir(DIRECTORY_MUSIC) : " + getExternalFilesDir(DIRECTORY_MUSIC).getAbsolutePath());
        Log.d(TAG, "getExternalFilesDirs() : " + Arrays.toString(getExternalFilesDirs("")));
        Log.d(TAG, "getExternalMediaDirs() : " + Arrays.toString(getExternalMediaDirs()));

        Log.d(TAG, "getCacheDir() : " + getCacheDir().getAbsolutePath());
        Log.d(TAG, "getCodeCacheDir() : " + getCodeCacheDir().getAbsolutePath());
        Log.d(TAG, "getDataDir() : " + getDataDir().getAbsolutePath());
        Log.d(TAG, "getDatabasePath() : " + getDatabasePath("db").getAbsolutePath());
        Log.d(TAG, "getFilesDir() : " + getFilesDir().getAbsolutePath());
        Log.d(TAG, "getObbDir() : " + getObbDir().getAbsolutePath());
        Log.d(TAG, "getObbDirs() : " + Arrays.toString(getObbDirs()));
        Log.d(TAG, "getDir() : " + getDir("",MODE_PRIVATE).getAbsolutePath());
        Log.d(TAG, "getFileStreamPath(stream) : " + getFileStreamPath("stream").getAbsolutePath());
    }
}
