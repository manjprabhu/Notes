package com.btk.notes.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NoteEntity.class} ,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase mInstance;

    public static NoteDatabase getInstance(Context context) {
        if(mInstance == null) {
            mInstance = Room.databaseBuilder(context,NoteDatabase.class, "note_database").addCallback(callback).build();
        }
        return mInstance;

    }

    public abstract NoteDao noteDao();


    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new insertAsyncTask(mInstance).execute();
        }

       /* @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new insertAsyncTask(mInstance).execute();
        }*/
    };


    private static class insertAsyncTask extends AsyncTask<Void, Void,Void> {

        private NoteDao noteDao;
        NoteEntity note;

        public insertAsyncTask(NoteDatabase database) {
            noteDao = database.noteDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new NoteEntity("Title3", "Description3",System.currentTimeMillis(), 3));
            noteDao.insert(new NoteEntity("Title2", "Description2",System.currentTimeMillis(),0));
            noteDao.insert(new NoteEntity("Title1", "Description1",System.currentTimeMillis()+20,6));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
