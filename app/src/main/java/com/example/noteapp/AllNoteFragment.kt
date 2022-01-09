package com.example.noteapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteapp.Firestore.Notes
import com.example.noteapp.databinding.FragmentAllNoteBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class AllNoteFragment : Fragment() {
    lateinit var binding: FragmentAllNoteBinding
    lateinit var adapter: NoteRecycleView
    private var db = Firebase.firestore
    val TAG: String = "AllNoteFragment"

    companion object list{
        lateinit var notesList: List<Notes>

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllNoteBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        val view = binding.root
        notesList = arrayListOf()
        adapter = NoteRecycleView(notesList as ArrayList<Notes>, activity?.applicationContext!!)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(activity?.applicationContext!!, 2)

        readFromDB()

        return view
    }
    fun readFromDB(){
        db.collection("Notes")
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                    val note = doc.toObjects(Notes::class.java)
                    notesList = note

                    adapter.update(notesList)
                    updateState(true)
                } else {
                    Log.d(TAG, "No such document")
                    updateState(false)
                }


            }
            .addOnFailureListener { e ->
                Log.e(TAG, e.localizedMessage)
                updateState(false)
            }
    }
    fun updateState(state: Boolean) {
        if (state) {
            binding.rv.isVisible = true
            binding.imageWrite.isVisible = false
            binding.tvTakeNote.isVisible = false
        } else {
            binding.rv.isVisible = false
            binding.imageWrite.isVisible = true
            binding.tvTakeNote.isVisible = true
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG,"onPause")
    }


}