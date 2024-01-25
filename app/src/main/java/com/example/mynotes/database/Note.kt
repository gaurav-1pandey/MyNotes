package com.example.mynotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id :Int?,
    @ColumnInfo(name = "title")
    var title : String?,
    @ColumnInfo(name = "note")
    var note : String?,
    @ColumnInfo(name = "date")
    var date : String?
) :java.io.Serializable
