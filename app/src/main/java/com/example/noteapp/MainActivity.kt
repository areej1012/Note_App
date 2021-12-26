package com.example.noteapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.GridView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var notesList : ArrayList<Note>
    lateinit var adapter: NoteRecycleView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)

        notesList = arrayListOf(Note("aa","tt", "Work"),Note("ll","dg", "Stuff"))
        adapter = NoteRecycleView(notesList)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this , 2)

    }

    fun openNote(view: View) {
        startActivity(Intent(this , NoteActivity::class.java))
    }

    fun getWorkCategory(view: View) {
        binding.btAll.background = resources.getDrawable(R.drawable.round_button)
        binding.btHome.background = resources.getDrawable(R.drawable.round_button)
        binding.btWork.background = resources.getDrawable(R.drawable.round_button_change)

    }
    fun getWorkHome(view: View) {
        binding.btAll.background = resources.getDrawable(R.drawable.round_button)
        binding.btHome.background = resources.getDrawable(R.drawable.round_button_change)
        binding.btWork.background = resources.getDrawable(R.drawable.round_button)
    }
    fun getAllCategory(view: View) {
        binding.btAll.background = resources.getDrawable(R.drawable.round_button_change)
        binding.btHome.background = resources.getDrawable(R.drawable.round_button)
        binding.btWork.background = resources.getDrawable(R.drawable.round_button)
    }

}