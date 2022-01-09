package com.example.noteapp.Firestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirestoreViewModel : ViewModel() {
    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = FirestoreRepository()
    var noteList: MutableLiveData<List<Notes>> = MutableLiveData()

    init {
        firebaseRepository.getNotes().get().addOnSuccessListener { doc ->
            var addressItem = doc.toObjects(Notes::class.java)
            noteList.value = addressItem
        }
            .addOnFailureListener{e->
                Log.e(TAG,e.localizedMessage)
                noteList.value = null
            }

    }
    fun getAllNote(): LiveData<List<Notes>> {
        return noteList
    }

    fun addNote(newNote : Notes){
        CoroutineScope(Dispatchers.IO).launch {
            firebaseRepository.addNote(newNote).addOnFailureListener{
                Log.e(TAG, "Failed to add")
            }
        }
    }

    fun deleteNote(note: Notes){
        CoroutineScope(Dispatchers.IO).launch {
            firebaseRepository.deleteNote(note).addOnFailureListener {
                Log.e(TAG, "Failed to delete")
            }
        }
    }
}