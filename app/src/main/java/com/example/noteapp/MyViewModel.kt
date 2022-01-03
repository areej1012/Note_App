package com.example.noteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteapp.DB.Note
import com.example.noteapp.DB.NoteDatabase
import com.example.noteapp.DB.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyViewModel(application: Application) : AndroidViewModel(application) {
    private var notes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).NoteDao()
        repository = NoteRepository(noteDao)
        notes = repository.getNotes
    }

    fun getNote(): LiveData<List<Note>> {
        return notes
    }

    fun saveDB(newNote: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addNote(newNote)
        }
    }

    fun updateNote(updateNote: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateNote(updateNote)
        }
    }

    fun deleteNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteNote(note)
        }
    }


}