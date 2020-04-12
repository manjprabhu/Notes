package com.btk.notes.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.btk.notes.R;

public class ColorsUtil {

    private Context mContext;

    public ColorsUtil(Context context) {
        this.mContext = context;
    }

    private void init() {
        TypedArray array = mContext.getResources().obtainTypedArray(R.array.color);
    }
}
