package com.example.noteapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.DB.DatabaseHelper
import com.example.noteapp.DB.NoteDatabase
import com.example.noteapp.Firestore.FirestoreViewModel
import com.example.noteapp.Firestore.Notes
import com.example.noteapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var notesList: List<Notes>
    private val noteDao by lazy { NoteDatabase.getDatabase(this).NoteDao() }
    lateinit var adapter: NoteRecycleView
    private val dbHelper by lazy { DatabaseHelper(applicationContext) }
    private val viewModel by lazy { ViewModelProvider(this).get(FirestoreViewModel::class.java) }
    val db = Firebase.firestore
    val TAG: String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)
        title = ""

//        notesList = arrayListOf()
//        adapter = NoteRecycleView(notesList as ArrayList<Notes>, this)
//        binding.rv.adapter = adapter
//        binding.rv.layoutManager = GridLayoutManager(this, 2)


    }

    override fun onResume() {
        super.onResume()
        readAllCategory()
    }


    fun openNote(view: View) {
        startActivity(Intent(this, NoteActivity::class.java))
    }

    fun getWorkCategory(view: View) {
        fragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().add(R.id.container, WorkNoteFragment()).commit()
        binding.btAll.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btAll.setTextColor(Color.rgb(156, 151, 151))
        binding.btHome.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btHome.setTextColor(Color.rgb(156, 151, 151))
        binding.btWork.backgroundTintList = ColorStateList.valueOf(Color.rgb(20, 159, 254))
        binding.btWork.setTextColor(Color.WHITE)

    }

    fun getHomeCategory(view: View) {

        supportFragmentManager.beginTransaction().add(R.id.container, HomeNoteFragment()).commit()

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
        supportFragmentManager.beginTransaction().add(R.id.container, AllNoteFragment()).commit()
        changeColorAllButton()
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