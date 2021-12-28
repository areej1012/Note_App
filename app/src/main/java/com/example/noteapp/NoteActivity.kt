package com.example.noteapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.noteapp.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    private val dbHelper by lazy { DatabaseHelper(applicationContext) }
    var category = "All"
    var isNoteSelected: Note? = null
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

        isNoteSelected = intent.extras?.getSerializable("Note") as? Note
        Log.e("is select create",isNoteSelected?.pk.toString())
    }

    override fun onResume() {
        super.onResume()
        Log.e("is select resume",isNoteSelected?.pk.toString())
        if (isNoteSelected != null) {
            binding.etTitle.setText(isNoteSelected?.Title)
            binding.etContent.setText(isNoteSelected?.Content)
            category = isNoteSelected!!.Category
            binding.listCatgory.setText(category)

        }
    }

    private fun categoryNote(i: Int): String {
        when (i) {
            0 -> return "Home"
            1 -> return "Work"
        }
        return ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addNote -> {
                if (isNoteSelected != null)
                    updateNote()
                else
                    addNote()
            }


        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNote() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        if (title.isEmpty() && content.isEmpty()) {
            finish()
        } else if (title.isEmpty() && content.isNotEmpty()) {
            val newNote = Note(
                null, "", content, category
            )
            dbHelper.saveData(newNote)
            finish()

        } else if (content.isEmpty() && title.isNotEmpty()) {
            val newNote = Note(
                null, title, "", category
            )
            dbHelper.saveData(newNote)
            finish()

        } else {
            val newNote = Note(
                null, title, content, category
            )
            dbHelper.saveData(newNote)
            finish()
        }

    }

    fun updateNote() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        if (title.isEmpty() && content.isEmpty()){
            dbHelper.deleteNote(isNoteSelected!!)
        }
        else{
            val updateNote = Note(isNoteSelected?.pk ,title , content, category)
            dbHelper.updateDate(updateNote)
            finish()
        }
    }
}