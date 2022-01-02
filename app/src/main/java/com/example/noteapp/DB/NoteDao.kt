package com.example.noteapp.DB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(newNote : Note)

    @Query("SELECT * FROM notes")
    fun getNote(): LiveData<List<Note>>

    @Update
    suspend fun updateNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)
}