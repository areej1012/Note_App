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
import com.example.noteapp.databinding.FragmentHomeNoteBinding
import com.example.noteapp.databinding.FragmentWorkNoteBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class WorkNoteFragment : Fragment() {
    lateinit var binding: FragmentWorkNoteBinding
    lateinit var adapter: NoteRecycleView
    private var db = Firebase.firestore
    val TAG: String = "WorkNoteFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkNoteBinding.inflate(inflater,container, false)
        val view = binding.root
        adapter = NoteRecycleView(AllNoteFragment.notesList as ArrayList<Notes>, activity?.applicationContext!!)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(activity?.applicationContext!!, 2)
        workNote()
        return inflater.inflate(R.layout.fragment_work_note, container, false)
    }

    private fun workNote() {
        val homeList = arrayListOf<Notes>()
        for (note in AllNoteFragment.notesList!!) {
            if (note.Category == "Work") {
                homeList.add(note)
                Log.e("title", note.Title.toString())
            }
        }
        if (homeList.isNotEmpty()) {
            adapter.update(homeList)
            updateState(true)
        } else
            updateState(false)
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

}