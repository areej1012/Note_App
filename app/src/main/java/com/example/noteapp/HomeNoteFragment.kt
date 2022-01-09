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
import com.example.noteapp.databinding.FragmentHomeNoteBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HomeNoteFragment : Fragment() {

    lateinit var binding: FragmentHomeNoteBinding
    lateinit var madapter: NoteRecycleView
    private var db = Firebase.firestore
    lateinit var noteHomeList : ArrayList<Notes>
    val TAG: String = "HomeNoteFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeNoteBinding.inflate(inflater,container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvH.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext!!, 2)
            noteHomeList = arrayListOf()
            madapter = NoteRecycleView(noteHomeList, activity?.applicationContext!!)
           adapter = madapter

        }
        HomeNote()
    }

    fun HomeNote(){

        for (note in AllNoteFragment.notesList!!) {
            if (note.Category == "Home") {
                noteHomeList.add(note)
            }
        }

        if (noteHomeList.isNotEmpty()) {
            madapter.update(noteHomeList)
            updateState(true)
        } else
            updateState(false)
    }

    fun updateState(state: Boolean) {
        if (state) {
            binding.rvH.isVisible = true
            binding.imageWrite.isVisible = false
            binding.tvTakeNote.isVisible = false
        } else {
            binding.rvH.isVisible = false
            binding.imageWrite.isVisible = true
            binding.tvTakeNote.isVisible = true
        }
    }
}