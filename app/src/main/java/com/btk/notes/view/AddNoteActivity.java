package com.btk.notes.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.btk.notes.R;
import com.btk.notes.adapters.ColorPickerAdapter;
import com.btk.notes.databinding.AddNoteLayoutBinding;
import com.btk.notes.model.NoteEntity;
import com.btk.notes.utils.Constants;
import com.btk.notes.utils.LogUtil;
import com.btk.notes.viewmodel.NoteViewModel;

public class AddNoteActivity extends AppCompatActivity {

    private final String TAG = AddNoteActivity.class.getSimpleName();

    private int mId, position;
    private String MODE;
    private AddNoteLayoutBinding mBinding;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.add_note_layout);
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (!isEmptyNote())
                    saveNote();
                else
                    Toast.makeText(this, getText(R.string.empty_title), Toast.LENGTH_LONG).show();
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
        LogUtil.LOGV(TAG, "init");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            mBinding.etTitle.setText(bundle.getString(Constants.NOTE_TITLE));
            mBinding.etDescription.setText(bundle.getString(Constants.NOTE_DESCRIPTION));
            mId = bundle.getInt(Constants.NOTE_ID);
            MODE = bundle.getString(Constants.MODE);

            if ("edit".equals(bundle.get(Constants.MODE))) {
                this.setTitle("Edit Note");
                position = bundle.getInt(Constants.NOTE_COLOR);
                mBinding.layoutConstraint.setBackgroundColor(Constants.getColor(position));
            } else {
                position = 1;
                this.setTitle("Create Note");
            }
        } else {
            position = 1;
            this.setTitle("Create Note");
        }
        LogUtil.LOGV(TAG, "init mode:" + MODE);
    }

    private void saveNote() {
        LogUtil.LOGV(TAG, "saveNote: position:" + position);
        NoteEntity entity = new NoteEntity(mBinding.etTitle.getText().toString(), mBinding.etDescription.getText().toString(), System.currentTimeMillis(), position);
        if (("edit").equalsIgnoreCase(MODE)) {
            entity.setId(mId);
            viewModel.updateNote(entity);
        } else {
            viewModel.createNewNote(entity);
        }
        supportFinishAfterTransition();
    }


    private void showColorPickerDialog() {
        GridView gridView = new GridView(this);
        ColorPickerAdapter adapter = new ColorPickerAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setNumColumns(3);
        gridView.setHorizontalSpacing(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        final AlertDialog show = builder.show();

        gridView.setOnItemClickListener((parent, view, pos, id) -> {
            mBinding.layoutConstraint.setBackgroundColor(Constants.getColor(pos));
            LogUtil.LOGV(TAG, "saveNote: position:" + pos);
            position = pos;
            show.dismiss();
        });
    }

    private boolean isEmptyNote() {
        return TextUtils.isEmpty(mBinding.etTitle.getText().toString()) && TextUtils.isEmpty(mBinding.etDescription.getText().toString()) ? true : false;
    }
}
