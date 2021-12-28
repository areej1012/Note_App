package com.example.noteapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var notesList : ArrayList<Note>
    lateinit var adapter: NoteRecycleView
    private val dbHelper by lazy { DatabaseHelper(applicationContext) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)
        title = ""

        notesList = arrayListOf()
        adapter = NoteRecycleView(notesList , this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this , 2)
        readAllCategory()


    }

    override fun onResume() {
        super.onResume()
        readAllCategory()
        changeColorAllButton()
    }


    fun openNote(view: View) {
        startActivity(Intent(this , NoteActivity::class.java))
    }

    fun getWorkCategory(view: View) {
        val workList = arrayListOf<Note>()
        for (note in notesList){
            if(note.Category == "Work")
                workList.add(note)
        }
        if(workList.isNotEmpty()){
            adapter.update(workList)
            updateState(true)
        }
        else
            updateState(false)


        binding.btAll.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btAll.setTextColor(Color.rgb(156,151,151))
        binding.btHome.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btHome.setTextColor(Color.rgb(156,151,151))
        binding.btWork.backgroundTintList = ColorStateList.valueOf(Color.rgb(20, 159, 254))
        binding.btWork.setTextColor(Color.WHITE)

    }

    fun getHomeCategory(view: View) {
        val homeList = arrayListOf<Note>()
        for (note in notesList){
            if(note.Category == "Home")
                homeList.add(note)
        }
        if(homeList.isNotEmpty()){
            adapter.update(homeList)
            updateState(true)
        }
        else
            updateState(false)

        binding.btAll.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btAll.setTextColor(Color.rgb(156,151,151))
        binding.btHome.backgroundTintList = ColorStateList.valueOf(Color.rgb(20, 159, 254))
        binding.btHome.setTextColor(Color.WHITE)
        binding.btWork.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btWork.setTextColor(Color.rgb(156,151,151))
    }


    fun getAllCategory(view: View) {
        readAllCategory()
        changeColorAllButton()

    }

    private fun readAllCategory() {
        notesList = dbHelper.getData()
        if(notesList.isNotEmpty()){
            adapter.update(notesList)
            updateState(true)
        }
        else
            updateState(false)
   }
    fun updateState(state :Boolean){
        if(state){
            binding.rv.isVisible = true
            binding.imageWrite?.isVisible = false
            binding.tvTakeNote?.isVisible = false
        }
        else{
            binding.rv.isVisible = false
           binding.imageWrite?.isVisible = true
            binding.tvTakeNote?.isVisible = true
        }


    }

    private fun changeColorAllButton() {
        binding.btAll.backgroundTintList = ColorStateList.valueOf(Color.rgb(20, 159, 254))
        binding.btAll.setTextColor(Color.WHITE)
        binding.btHome.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btHome.setTextColor(Color.rgb(156,151,151))
        binding.btWork.backgroundTintList = ColorStateList.valueOf(Color.rgb(235, 235, 235))
        binding.btWork.setTextColor(Color.rgb(156,151,151))
    }

}