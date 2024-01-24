package com.example.mynotes.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val repo : NoteRepo

    val allnotes:  LiveData<List<Note>>

    init {
        val dao=NoteDatabase.getDatabase(application).getNoteDao()
        repo=NoteRepo(dao)
        allnotes =repo.allNotes
    }

    fun deletenote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(note)
        }
    }
    fun insertnote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(note)
        }
    }
    fun updatenote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(note)
        }
    }
}