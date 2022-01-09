package com.example.noteapp.Firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//This class will mange DB
class FirestoreRepository {
    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = Firebase.firestore


    fun getNotes(): CollectionReference {
        var collectionReference = firestoreDB.collection("Notes")
        return collectionReference
    }
    fun deleteNote(note : Notes): Task<Void>{
        val documentReference = firestoreDB.collection("Notes")
            .document(note.pk!!)
        return documentReference.delete()

    }

    fun addNote(newNote : Notes) : Task<Void>{
        val documentReference =  firestoreDB.collection("Notes")
            .document(newNote.pk!!)
        return  documentReference.set(newNote)
    }
}