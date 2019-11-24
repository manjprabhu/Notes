package com.btk.notes.Model;


import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface CheckListDao {

    @Insert
    void insert(CheckListEntity entity);
}
