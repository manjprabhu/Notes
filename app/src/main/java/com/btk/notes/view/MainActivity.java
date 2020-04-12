package com.btk.notes.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btk.notes.model.NoteEntity;
import com.btk.notes.R;
import com.btk.notes.utils.Constants;
import com.btk.notes.viewmodel.NoteViewModel;
import com.btk.notes.adapters.NotesListAdapter;
import com.btk.notes.databinding.ActivityMainBinding;
import com.btk.notes.interfaces.ButtonClickCallback;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NotesListAdapter.onItemClickListener, ButtonClickCallback {

    private final String TAG = MainActivity.class.getSimpleName();

    private NoteViewModel mNoteViewModel;
    private NotesListAdapter mAdapter;
    private NoteEntity mDeletedNote;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setButtomClickCallback(this);
        mBinding.rvNotesList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotesListAdapter(this);

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, notes -> {
            Log.v(TAG, "Length:" + notes.size());
            mBinding.rvNotesList.setAdapter(mAdapter);
            mAdapter.SetData(notes);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mDeletedNote = mAdapter.getItem(viewHolder.getAdapterPosition());
                mNoteViewModel.deleteNote(mAdapter.getItem(viewHolder.getAdapterPosition()));
                showUndoSnackbar(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(mBinding.rvNotesList);
    }

    public void EditNote(NoteEntity entity) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NOTE_TITLE, entity.getTitle());
        bundle.putString(Constants.NOTE_DESCRIPTION, entity.getDescription());
        bundle.putString(Constants.MODE, "edit");
        bundle.putInt(Constants.NOTE_ID, entity.getId());
        bundle.putInt(Constants.NOTE_COLOR, entity.getBgColor());
        intent.putExtras(bundle);
        intent.setClass(this, AddNoteActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "onActivityResult");

        if (resultCode == RESULT_OK && requestCode == Constants.CREATE_NOTE) {
            if (data != null && data.getExtras() != null) {
                Bundle bundle = data.getExtras();

                NoteEntity noteEntity = new NoteEntity(bundle.get(Constants.NOTE_TITLE).toString(), bundle.get(Constants.NOTE_DESCRIPTION).toString(), bundle.getLong(Constants.NOTE_CREATE_DATE), bundle.getInt(Constants.NOTE_COLOR));
                mNoteViewModel.createNewNote(noteEntity);
            }
        } else if (resultCode == RESULT_OK && requestCode == Constants.UPDATE_NOTE) {
            if (data != null && data.getExtras() != null) {
                Bundle bundle = data.getExtras();
                NoteEntity noteEntity = new NoteEntity(bundle.get(Constants.NOTE_TITLE).toString(), bundle.get(Constants.NOTE_DESCRIPTION).toString(), bundle.getLong(Constants.NOTE_CREATE_DATE), bundle.getInt(Constants.NOTE_COLOR));
                noteEntity.setId(bundle.getInt(Constants.NOTE_ID));
                mNoteViewModel.updateNote(noteEntity);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                mNoteViewModel.deleteAllNotes();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(int pos) {
        NoteEntity noteEntity = mAdapter.getItem(pos);
        Log.v(TAG, "Title:" + noteEntity.getTitle()+" Description:"+noteEntity.getDescription());
        EditNote(noteEntity);
    }

    private void showUndoSnackbar(int i) {
        Snackbar snackbar = Snackbar.make(mBinding.idConstraintLayout, R.string.delete_note_snackbar, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo_delete, v -> { undoDelete(i);  });
                snackbar.show();
    }

    private void undoDelete(int i) {
    }

    @Override
    public void createNote() {
        Intent intent = new Intent();
        intent.setClass(this,AddNoteActivity.class);
        startActivity(intent);
    }
}
