package com.btk.notes.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.btk.notes.model.NoteEntity;
import com.btk.notes.repositories.NoteRepository;

import java.util.List;

import io.reactivex.Flowable;

public class NoteViewModel extends AndroidViewModel {

    private Flowable<List<NoteEntity>> mAllNotes;
    private NoteRepository mRepository;

    public NoteViewModel(Application application) {
        super(application);
        mRepository = new NoteRepository(application);
//        mAllNotes = mRepository.getAllNotes();
    }

    public Flowable<List<NoteEntity>> getAllNotes() {
        return mRepository.getAllNotes();
    }

    public void createNewNote(NoteEntity entity) {
        mRepository.CreateNewNote(entity);
    }

    public void updateNote(NoteEntity entity) {
        mRepository.UpdateNote(entity);
    }

    public void deleteAllNotes() {
        mRepository.DeleteAllNotes();
    }

    public void deleteNote(NoteEntity note) {
        mRepository.DeleteNote(note);
    }

    public void undoDelete(NoteEntity note) {
        mRepository.undoDelete(note);
    }

}
