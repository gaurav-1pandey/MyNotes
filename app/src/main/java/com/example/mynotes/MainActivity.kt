package com.example.mynotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView

import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.adapter.NotesAdapter

import com.example.mynotes.database.Note
import com.example.mynotes.database.NoteDatabase
import com.example.mynotes.database.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), NotesAdapter.NotesClickListner,PopupMenu.OnMenuItemClickListener {
    private lateinit var database :NoteDatabase
    lateinit var viewmodel: NoteViewModel
    lateinit var searchView: SearchView
    lateinit var adapter :NotesAdapter
    lateinit var selectedNote:Note
    lateinit var recyclerView: RecyclerView
    lateinit var floatingActionButton: FloatingActionButton

    private val update=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode==Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as? Note
            if (note!=null){

                viewmodel.updatenote(note)
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatingActionButton=findViewById(R.id.floatingActionButton)
        searchView=findViewById(R.id.searchView)

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
        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter= NotesAdapter(this,this)
        recyclerView.adapter=adapter


        val getContent=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
            if (result.resultCode== Activity.RESULT_OK){
                val note=result.data?.getSerializableExtra("note") as? Note
                if (note!=null){
                    viewmodel.insertnote(note)
                }
            }

        }
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddNote::class.java)
            getContent.launch(intent)
        }

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!=null){
                    adapter.filter(newText)
                }
                return true
            }

        }
        )


    }

    override fun onClickListner(note: Note) {
        val intent = Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra("current_note",note)
        update.launch(intent)
    }

    override fun onLongClickListner(note: Note, cardView: CardView) {
        selectedNote=note
        popupdisplay(cardView)
    }

    private fun popupdisplay(cardView: CardView) {
        val popup = PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.popup_del)
        popup.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId==R.id.del){
            viewmodel.deletenote(selectedNote)
            return true
        }
        return false
    }
}