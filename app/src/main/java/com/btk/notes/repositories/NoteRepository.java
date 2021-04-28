package com.btk.notes.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.btk.notes.model.NoteDao;
import com.btk.notes.model.NoteDatabase;
import com.btk.notes.model.NoteEntity;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<NoteEntity>> mAllNotes;

    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
        noteDao = db.noteDao();
//        mAllNotes = noteDao.getAllNotes();
//        mAllNotes = noteDao.getSortedNotes();
        mAllNotes = noteDao.getNotes();
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

    public void undoDelete(NoteEntity entity) {
        new undoDeleteTask().execute(entity);
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
//            noteDao.delete(noteEntities[0]);
            noteDao.markAsDeleted(1,noteEntities[0].getId());
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

    class undoDeleteTask extends AsyncTask<NoteEntity,Void,Void> {
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.undoDelete(0,noteEntities[0].getId());
            return null;
        }
    }
}
