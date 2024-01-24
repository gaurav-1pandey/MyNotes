package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.adapter.NotesAdapter
import com.example.mynotes.database.Note
import com.example.mynotes.database.NoteDatabase
import com.example.mynotes.database.NoteViewModel
import com.example.roomdbdemo.R

class MainActivity : AppCompatActivity() {
    private lateinit var database :NoteDatabase
    lateinit var viewmodel: NoteViewModel
    lateinit var adapter :NotesAdapter
     lateinit var selectedNote:Note
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel=ViewModelProvider(this,ViewModelProvider.
        AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewmodel.allnotes.observe(this) {list ->
            list?.let{
                adapter.updatelist(list)
            }
        }

        database=NoteDatabase.getDatabase(this)
        initUI()
    }

    private fun initUI() {

    }
}