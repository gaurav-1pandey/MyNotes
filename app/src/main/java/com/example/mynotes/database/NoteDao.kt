package com.example.mynotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note :Note)

    @Query("UPDATE tb_note set title =:title , note=:note WHERE id=:id")
    suspend fun update(id :Int?, title: String?, note : String?)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM tb_note ORDER BY id ASC")
    fun get_all_notes():LiveData<List<Note>>
}