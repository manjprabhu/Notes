package com.btk.notes;

import android.app.Application;
import android.util.Log;

public class Notes extends Application {

    private static Notes mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    public static Notes getAppContext() {
        return mAppContext;
    }
}
