package com.example.vocab_master_mad_project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface VocabDao {
    @Query("SELECT * FROM vocabs ORDER BY english ASC")
    List<Vocab> getAllVocabs();

    @Query("SELECT * FROM vocabs WHERE difficulty = :difficulty ORDER BY english ASC")
    List<Vocab> getVocabsByDifficulty(String difficulty);

    @Query("SELECT DISTINCT difficulty FROM vocabs")
    List<String> getAllGroups();

    @Insert
    void insert(Vocab vocab);

    @Insert
    void insertAll(List<Vocab> vocabs);

    @Update
    void update(Vocab vocab);

    @Delete
    void delete(Vocab vocab);

    @Query("DELETE FROM vocabs WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM vocabs WHERE difficulty = :difficulty")
    void deleteGroupByDifficulty(String difficulty);
}