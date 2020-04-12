package com.btk.notes.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.btk.notes.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddCheckListActivity extends AppCompatActivity {

    private LinearLayout mParentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_list);

        mParentLayout = (LinearLayout) findViewById(R.id.id_linearlayout);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewItem();

            }
        });
    }

    private void addnewItem() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rootView = inflater.inflate(R.layout.checklist_item, null);

        mParentLayout.addView(rootView);
    }

    public void onDeleteItem(View view) {
        mParentLayout.removeView((View) view.getParent());
    }
}
