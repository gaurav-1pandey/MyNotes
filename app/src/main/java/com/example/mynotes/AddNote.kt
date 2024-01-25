package com.example.mynotes

import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mynotes.database.Note
import java.text.SimpleDateFormat
import java.util.*


class AddNote : AppCompatActivity() {
    private lateinit var note: Note
    private lateinit var old_note: Note
    lateinit var edtitle: EditText
    lateinit var ednote: EditText
    var isupdate=false
    lateinit var imageViewcheck: ImageView
    lateinit var imageViewclose: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        ednote=findViewById(R.id.ednote)
        edtitle=findViewById(R.id.edtitle)
        imageViewcheck=findViewById(R.id.image_check)
        imageViewclose=findViewById(R.id.image_back)
        try {
            old_note=intent.getSerializableExtra("current_note") as Note
            edtitle.setText(old_note.title)
            ednote.setText(old_note.note)
            isupdate=true


        }
        catch (e: Exception){
            e.printStackTrace()

        }

        imageViewcheck.setOnClickListener({
            val title = edtitle.text.toString()
            val notee=ednote.text.toString()
            if ( title.isNotEmpty() || notee.isNotEmpty()){
                val formattor=SimpleDateFormat("EEE d MMM yyyy HH:mm a")

                if (isupdate){
                    note=Note(old_note.id,title,notee,formattor.format(Date()))
                }else {
                    note = Note(null,title,notee,formattor.format(Date()))
                }
                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            else{
                Toast.makeText(this@AddNote,"Enter some data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        })

        imageViewclose.setOnClickListener({
            onBackPressed()

        })
    }
}