package com.btk.notes.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.btk.notes.model.NoteDao;
import com.btk.notes.model.NoteDatabase;
import com.btk.notes.model.NoteEntity;
import com.btk.notes.utils.LogUtils;

import java.util.List;

import io.reactivex.Flowable;

public class NoteRepository {

    private final String TAG = NoteRepository.class.getSimpleName();
    private NoteDao noteDao;

    private LiveData<List<NoteEntity>> mAllNotes;

    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
        noteDao = db.noteDao();
//        mAllNotes = noteDao.getAllNotes();
//        mAllNotes = noteDao.getSortedNotes();
//        mAllNotes = noteDao.getNotes();
    }

    public Flowable<List<NoteEntity>> getAllNotes() {
        return noteDao.getAllNotes();
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
            LogUtils.LOGV(TAG, "InsertTask");
            noteDao.insert(noteEntities[0]);
            return null;
        }
    }

    class DeleteAllNotesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            LogUtils.LOGV(TAG, "DeleteAllNotesTask");
            noteDao.deleteAllNotes();
            return null;
        }
    }

    class DeleteSingleNote extends AsyncTask<NoteEntity, Void, Void> {
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
//            noteDao.delete(noteEntities[0]);
            LogUtils.LOGV(TAG, "DDeleteSingleNoteTask");
            noteDao.markAsDeleted(1, noteEntities[0].getId());
            return null;
        }
    }

    class UpdateNoteTask extends AsyncTask<NoteEntity, Void, Void> {

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            LogUtils.LOGV(TAG, "UpdateNoteTask");
            noteDao.update(noteEntities[0]);
            return null;
        }
    }

    class undoDeleteTask extends AsyncTask<NoteEntity, Void, Void> {
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            LogUtils.LOGV(TAG, "undoDeleteTask");
            noteDao.undoDelete(0, noteEntities[0].getId());
            return null;
        }
    }
}
