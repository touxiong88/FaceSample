package com.example.administrator;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;


/**
 * Created by liujie on 2017/7/4.
 */

public class MainApplication extends Application {
    public static Bitmap mRotaBitmap;
    private static Context context;
    // 默认发音人
    public static String voicer = "mengmeng";
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static Context getMainApplicationContext(){
        return  context;
    }
}
