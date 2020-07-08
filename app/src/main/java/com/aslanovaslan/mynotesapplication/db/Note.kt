package com.aslanovaslan.mynotesapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Note(
    @ColumnInfo(name = "note_title")
    var title:String,
    @ColumnInfo(name = "note_body")
    var note:String

):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}