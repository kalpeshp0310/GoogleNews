package com.kalpesh.googlenews.utils;

import android.util.Log;

/**
 * Created by abc on 30-09-2014.
 */
public class LogUtils {

    private static final boolean LOG = true;

    public static void v(String tag, String msg){
        if(LOG)
            Log.v(tag, msg);
    }

    public static void i(String tag, String msg){
        if(LOG)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg){
        if(LOG)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg){
        if(LOG)
            Log.e(tag, msg);
    }
}
