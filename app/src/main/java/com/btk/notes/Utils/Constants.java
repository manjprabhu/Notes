package com.btk.notes.Utils;

import android.content.res.TypedArray;

import com.btk.notes.Notes;
import com.btk.notes.R;

public class Constants {

    public final static String NOTE_TITLE = "note_title";
    public final static String NOTE_DESCRIPTION = "note_description";
    public final static String NOTE_CREATE_DATE = "create_date";
    public final static String NOTE_COLOR = "note_color";
    public final static String NOTE_ID = "note_id";
    public final static String MODE = "mode";

    public static final int CREATE_NOTE = 1000;
    public static final int UPDATE_NOTE = 1100;

    public static int getColor(int pos) {
        TypedArray array = Notes.getAppContext().getResources().obtainTypedArray(R.array.color);
        return array.getColor(pos, 0);
    }
}
