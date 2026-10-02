package com.example.vocab_master_mad_project;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "vocabs")
public class Vocab implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    @NonNull
    private String english;
    private String pos;
    private String chinese;
    private String difficulty; // "Basic", "Enhanced", "Elite"

    public Vocab(@NonNull String english, String pos, String chinese, String difficulty) {
        this.english = english;
        this.pos = pos;
        this.chinese = chinese;
        this.difficulty = difficulty;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getEnglish() { return english; }
    public void setEnglish(@NonNull String english) { this.english = english; }

    public String getPos() { return pos; }
    public void setPos(String pos) { this.pos = pos; }

    public String getChinese() { return chinese; }
    public void setChinese(String chinese) { this.chinese = chinese; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    @Override
    public String toString() {
        return english + " (" + pos + ") - " + chinese;
    }
}