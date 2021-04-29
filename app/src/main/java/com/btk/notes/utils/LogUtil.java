package com.btk.notes.utils;

import android.util.Log;

import com.btk.notes.BuildConfig;

public class LogUtil {

    public static void LOGV(final String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void LOGD(final String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void LOGE(final String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void LOGI(final String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }
}
