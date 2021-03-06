package com.lib.android.base.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 *  CrashHandler
 *
 *      在自定义 Application 中的 onCreate() 方法中添加一下语句
 *
 *      CrashHandler.getInstance().init(getApplicationContext(),"ClientAppName");
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static String TAG = "CherryCrash";
    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;
    private String clientApp;

    // 用来存储设备信息和异常信息
    private Map<String, String> errInfo = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    @SuppressLint("SimpleDateFormat")
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        autoClear(5);
    }
    
    public void init(Context context,String name) {
        init(context);
        clientApp = name;
    }

	/**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            SystemClock.sleep(3000);
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null)
            return false;

        try {
            // 使用Toast来显示异常信息
            new Thread() {

                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext,"crash", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            // 收集设备参数信息
            collectDeviceInfo(mContext);
            // 保存日志文件
            saveCrashInfoFile(ex);
            SystemClock.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                errInfo.put("AppName", clientApp != null ? clientApp : ctx.getPackageName());
                errInfo.put("versionName", pi.versionName);
                errInfo.put("versionCode", pi.versionCode+"");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occur when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                errInfo.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occur when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     * @throws Exception
     */
    @SuppressLint("SimpleDateFormat")
	private String saveCrashInfoFile(Throwable ex) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            String date = sDateFormat.format(new Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : errInfo.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            String fileName = writeFile(sb.toString());
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occur while writing file...", e);
            sb.append("an error occur while writing file...\r\n");
            writeFile(sb.toString());
        }
        
        Log.e("error", ex.toString());
        
        return null;
    }

    private String writeFile(String sb) throws Exception {
        String time = formatter.format(new Date());
        String fileName = "crash-" + clientApp + "-" + time + ".log";

        String path = mContext.getExternalCacheDir().getAbsolutePath()+ File.separator + "crash" + File.separator;
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }

        FileOutputStream fos = new FileOutputStream(path + fileName, true);
        fos.write(sb.getBytes());
        fos.flush();
        fos.close();
        return fileName;
    }

    /**
     * 文件删除
     *
     * @param autoClearDay 文件保存天数
     */
    public void autoClear(final int autoClearDay) {
//        FileUtil.delete(getGlobalPath(), new FilenameFilter() {
//
//            @Override
//            public boolean accept(File file, String filename) {
//                String s = FileUtil.getFileNameWithoutExtension(filename);
//                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
//                String date = "crash-" + DateUtil.getOtherDay(day);
//                return date.compareTo(s) >= 0;
//            }
//        });
    }

}
