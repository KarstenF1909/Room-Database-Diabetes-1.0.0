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
    private float bolus;
    private float korrektur;
    private float basal;


    //Constructor
    public Note(String title, String description, int priority,int blutzucker,float be,float bolus,float korrektur,float basal) {
        this.title = title;
        this.description = description;
        this.priority = priority;

        this.blutzucker = blutzucker;
        this.be = be;
        this.bolus = bolus;
        this.korrektur = korrektur;
        this.basal = basal;
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

    public float getBolus() {
        return bolus;
    }

    public float getKorrektur() {
        return korrektur;
    }

    public float getBasal() {
        return basal;
    }
}
