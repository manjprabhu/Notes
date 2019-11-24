package com.btk.notes.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CheckListEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    private String content;

    public CheckListEntity(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
