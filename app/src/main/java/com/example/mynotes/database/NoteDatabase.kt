package com.example.mynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase :RoomDatabase(){
    abstract fun getNoteDao() : NoteDao

    companion object{
        @Volatile // volatile is used to make object or variable visible to all the classes
        private var INSTANCE:NoteDatabase? = null
        //singleton pattern
        fun getDatabase(context :Context):NoteDatabase{
            val tempinstance= INSTANCE
            if (tempinstance != null){
                return tempinstance
            }
            val instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "mydb").build()

            INSTANCE=instance
            return instance

        }

    }
}