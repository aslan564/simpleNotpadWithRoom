package com.aslanovaslan.mynotesapplication.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend  fun insertNote(note:Note)

    @Insert
    suspend fun insertMultipleNote(vararg note:Note)

    @Query("select * from notes_table")
    suspend fun getAllNotes():List<Note>

    @Update
    suspend fun updateNot(note: Note)

    @Delete
    suspend fun deleteNot(note: Note)
}