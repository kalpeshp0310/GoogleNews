package com.kalpesh.googlenews.utils;

import android.app.Activity;

import com.devspark.appmsg.AppMsg;

/**
 * Created by abc on 02-10-2014.
 */
public class AppMsgUtils {

    public static void showAlertMsg(Activity activity, String msg){
        AppMsg.makeText(activity, msg, AppMsg.STYLE_ALERT).show();
    }

    public static void showInfotMsg(Activity activity, String msg){
        AppMsg.makeText(activity, msg, AppMsg.STYLE_INFO).show();
    }

    public static void showConfirmationMsg(Activity activity, String msg){
        AppMsg.makeText(activity, msg, AppMsg.STYLE_CONFIRM).show();
    }
}
