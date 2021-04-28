package com.btk.notes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int id;

    @ColumnInfo(name = "priority")
    private int priority;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "create_date")
    private long createdDate;

    @ColumnInfo(name = "bg_color")
    private int bgColor;

    @ColumnInfo(name = "isDeleted")
    private int isdeleted;

    public NoteEntity(String title, String description, long createdDate, int bgColor) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.bgColor = bgColor;
        this.isdeleted = 0;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    public int getBgColor() {
        return bgColor;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getCreatedDate() {
        return createdDate;
    }
}
