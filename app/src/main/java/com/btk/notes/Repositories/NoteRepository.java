package com.btk.notes.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.btk.notes.Model.NoteDao;
import com.btk.notes.Model.NoteDatabase;
import com.btk.notes.Model.NoteEntity;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<NoteEntity>> mAllNotes;

    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
        noteDao = db.noteDao();
//        mAllNotes = noteDao.getAllNotes();
        mAllNotes = noteDao.getSortedNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return mAllNotes;
    }

    public void CreateNewNote(NoteEntity entity) {
        new InsertTask().execute(entity);
    }

    public void DeleteAllNotes() {
        new DeleteAllNotesTask().execute();
    }

    public void DeleteNote(NoteEntity note) {
        new DeleteSingleNote().execute(note);

    }

    public void UpdateNote(NoteEntity entity) {
        new UpdateNoteTask().execute(entity);
    }

    class InsertTask extends AsyncTask<NoteEntity, Void, Void> {
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.insert(noteEntities[0]);
            return null;
        }
    }

    class DeleteAllNotesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

    class DeleteSingleNote extends AsyncTask<NoteEntity, Void, Void> {
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.delete(noteEntities[0]);
            return null;
        }
    }

    class UpdateNoteTask extends AsyncTask<NoteEntity, Void, Void> {

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.update(noteEntities[0]);
            return null;
        }
    }
}
