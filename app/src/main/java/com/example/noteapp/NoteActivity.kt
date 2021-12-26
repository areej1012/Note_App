package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.noteapp.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    private val dbHelper by lazy { DatabaseHelper(applicationContext) }
    var category = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""
        val toolbar = binding.toolbarNote as Toolbar
        setSupportActionBar(toolbar)
        val categoryList = arrayListOf("Home", "Work")
        val adpterArray = ArrayAdapter(this, R.layout.dropdwon_item, categoryList)
        binding.listCatgory.setAdapter(adpterArray)
        binding.listCatgory.setOnItemClickListener { _, _, i, _ ->
            category = categoryNote(i)
        }
    }

    private fun categoryNote(i: Int): String {
        when (i) {
            0 -> return "Home"
            1 -> return "Work"
        }
        return "Home"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNote -> addNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNote() {
        val title = binding.tvTitle.text.toString()
        val content = binding.tvContent.text.toString()
        if (title.isEmpty() && content.isEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else if (title.isEmpty()) {
            val newNote = Note(
                "", content, category
            )
               dbHelper.saveData(newNote)
            startActivity(Intent(this, MainActivity::class.java))
        } else if (content.isEmpty()) {
            val newNote = Note(
                title, "", category
            )
             dbHelper.saveData(newNote)
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            val newNote = Note(
                title, content, category
            )
              dbHelper.saveData(newNote)
            startActivity(Intent(this, MainActivity::class.java))

        }

        Toast.makeText(this, "Save Done", Toast.LENGTH_LONG).show()
    }
}