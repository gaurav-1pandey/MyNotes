package com.example.mynotes.database

import androidx.lifecycle.LiveData

class NoteRepo(private val noteDao: NoteDao) {

    val allNotes : LiveData<List<Note>> =noteDao.get_all_notes()

    suspend fun insert(note:Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note.id,note.title,note.note)
    }
}