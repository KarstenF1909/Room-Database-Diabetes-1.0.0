package com.karstenfischer.room.roomdatabasegithubtest;

//Das hier ist die Entity (Eine Tabelle in der...)

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;

    private int blutzucker;
    private float be;


    //Constructor
    public Note(String title, String description, int priority,int blutzucker,float be) {
        this.title = title;
        this.description = description;
        this.priority = priority;

        this.blutzucker = blutzucker;
        this.be = be;
    }


    //Setter
    public void setId(int id) {
        this.id = id;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public int getBlutzucker() {
        return blutzucker;
    }

    public float getBe() {
        return be;
    }
}
