package com.btk.notes.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.btk.notes.R;
import com.btk.notes.Utils.Constants;
import com.btk.notes.adapters.ColorPickerAdapter;

public class AddNoteActivity extends AppCompatActivity {

    private final String TAG  = AddNoteActivity.class.getSimpleName();

    private EditText mTitle,mDescription;
    private ConstraintLayout mLayout;
    private int mId,position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_layout);

        mLayout = (ConstraintLayout)findViewById(R.id.layout_constraint);
        mTitle = (EditText)findViewById(R.id.et_title);
        mDescription =(EditText)findViewById(R.id.et_description);
        position = 1;

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_create_note,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if(!isEmptyNote())
                    saveNote();
                else
                    finish();
                break;
            case R.id.action_color_picker:
                showColorPickerDialog();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle !=null) {
            mTitle.setText(bundle.getString(Constants.NOTE_TITLE));
            mDescription.setText(bundle.getString(Constants.NOTE_DESCRIPTION));
            mId = bundle.getInt(Constants.NOTE_ID);
            mLayout.setBackgroundColor(Constants.getColor(bundle.getInt(Constants.NOTE_COLOR)));
            if("edit".equals(bundle.get(Constants.MODE))) {
                this.setTitle("Edit Note");
            } else {
                this.setTitle("Create Note");
            }
        } else {
            this.setTitle("Create Note");
        }
    }

    private void saveNote() {
        Log.v(TAG,"saveNote: position:"+position);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NOTE_TITLE,mTitle.getText().toString());
        bundle.putString(Constants.NOTE_DESCRIPTION, mDescription.getText().toString());
        bundle.putLong(Constants.NOTE_CREATE_DATE,System.currentTimeMillis());
        bundle.putInt(Constants.NOTE_ID,mId);
        bundle.putInt(Constants.NOTE_COLOR,position);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }


    private void showColorPickerDialog()
    {
        GridView gridView = new GridView(this);
        ColorPickerAdapter adapter = new ColorPickerAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(3);
        gridView.setHorizontalSpacing(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        final AlertDialog show = builder.show();

        gridView.setOnItemClickListener((parent, view, pos, id) -> {
            mLayout.setBackgroundColor(Constants.getColor(pos));
            Log.v(TAG,"saveNote: position:"+pos);
            position = pos;
            show.dismiss();
        });
    }

    private boolean isEmptyNote() {
        return TextUtils.isEmpty(mTitle.getText().toString()) && TextUtils.isEmpty(mDescription.getText().toString()) ? true  : false;
    }
}
