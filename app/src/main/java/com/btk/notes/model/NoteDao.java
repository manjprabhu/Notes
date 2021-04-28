package com.btk.notes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Update
    void update(NoteEntity entity);

    @Delete
    void delete(NoteEntity entity);

    @Insert
    void insert(NoteEntity note);

    @Query("SELECT * FROM note_table")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY create_date DESC")
    LiveData<List<NoteEntity>> getSortedNotes();

    @Query("UPDATE note_table SET isDeleted =:isDeleted WHERE note_id = :noteId")
    void markAsDeleted(int isDeleted, int noteId);

    @Query("SELECT * FROM note_table WHERE isDeleted = 0 ORDER BY create_date DESC")
    LiveData<List<NoteEntity>> getNotes();

    @Query("UPDATE note_table SET isDeleted =:isDeleted WHERE note_id = :noteId")
    void undoDelete(int isDeleted, int noteId);
}
