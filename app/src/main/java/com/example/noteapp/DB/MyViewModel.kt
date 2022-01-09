package com.example.noteapp.DB

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteapp.DB.Note
import com.example.noteapp.DB.NoteRepository
import com.example.noteapp.Firestore.Notes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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