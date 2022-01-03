package com.example.noteapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteapp.DB.DatabaseHelper
import com.example.noteapp.DB.NoteDatabase
import com.example.noteapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var notesList: List<Notes>
    private val noteDao by lazy { NoteDatabase.getDatabase(this).NoteDao() }
    lateinit var adapter: NoteRecycleView
    private val dbHelper by lazy { DatabaseHelper(applicationContext) }
    private val viewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java) }
    val db = Firebase.firestore
    val TAG: String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)
        title = ""

        notesList = arrayListOf()
        adapter = NoteRecycleView(notesList as ArrayList<Notes>, this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this, 2)

    }

    override fun onResume() {
        super.onResume()
        readAllCategory()
    }


    fun openNote(view: View) {
        startActivity(Intent(this, NoteActivity::class.java))
    }

    fun getWorkCategory(view: View) {
        val workList = arrayListOf<Notes>()
        for (note in notesList) {
            if (note.Category == "Work")
                workList.add(note)
        }
        if (workList.isNotEmpty()) {
            adapter.update(workList)
            updateState(true)
        } else
            updateState(false)


        binding.btAll.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btAll.setTextColor(Color.rgb(156, 151, 151))
        binding.btHome.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btHome.setTextColor(Color.rgb(156, 151, 151))
        binding.btWork.backgroundTintList = ColorStateList.valueOf(Color.rgb(20, 159, 254))
        binding.btWork.setTextColor(Color.WHITE)

    }

    fun getHomeCategory(view: View) {
        val homeList = arrayListOf<Notes>()
        for (note in notesList) {
            if (note.Category == "Home")
                homeList.add(note)
        }
        if (homeList.isNotEmpty()) {
            adapter.update(homeList)
            updateState(true)
        } else
            updateState(false)

        binding.btAll.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btAll.setTextColor(Color.rgb(156, 151, 151))
        binding.btHome.backgroundTintList = ColorStateList.valueOf(Color.rgb(20, 159, 254))
        binding.btHome.setTextColor(Color.WHITE)
        binding.btWork.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btWork.setTextColor(Color.rgb(156, 151, 151))
    }


    fun getAllCategory(view: View) {
        readAllCategory()
        changeColorAllButton()

    }

    fun readAllCategory() {

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

        changeColorAllButton()
    }

    fun updateState(state: Boolean) {
        if (state) {
            binding.rv.isVisible = true
            binding.imageWrite?.isVisible = false
            binding.tvTakeNote?.isVisible = false
        } else {
            binding.rv.isVisible = false
            binding.imageWrite?.isVisible = true
            binding.tvTakeNote?.isVisible = true
        }


    }

    private fun changeColorAllButton() {
        binding.btAll.backgroundTintList = ColorStateList.valueOf(Color.rgb(20, 159, 254))
        binding.btAll.setTextColor(Color.WHITE)
        binding.btHome.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btHome.setTextColor(Color.rgb(156, 151, 151))
        binding.btWork.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btWork.setTextColor(Color.rgb(156, 151, 151))
    }

}