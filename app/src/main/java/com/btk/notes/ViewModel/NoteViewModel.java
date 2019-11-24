package com.btk.notes.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.btk.notes.Model.NoteEntity;
import com.btk.notes.Repositories.NoteRepository;


import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private LiveData<List<NoteEntity>> mAllNotes;
    private NoteRepository mRepository;

    public LiveData<List<NoteEntity>> getAllNotes() {
        return mAllNotes;
    }

    public NoteViewModel(Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
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
}
