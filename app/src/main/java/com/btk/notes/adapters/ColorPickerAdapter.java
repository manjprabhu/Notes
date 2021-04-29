package com.btk.notes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.btk.notes.R;
import com.btk.notes.utils.Constants;

public class ColorPickerAdapter extends BaseAdapter {

    private final Context mContext;
    private onColorpaletteClick mClick;

    public ColorPickerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View colorView;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.color_picker_item, parent, false);
        colorView = (View) view.findViewById(R.id.color_item);
        colorView.setBackgroundColor(Constants.getColor(position));
        return view;
    }

    public interface onColorpaletteClick {
        void onClick(int pos);
    }
}
