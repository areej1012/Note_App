package com.example.noteapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.DB.DatabaseHelper
import com.example.noteapp.DB.MyViewModel
import com.example.noteapp.DB.NoteDatabase
import com.example.noteapp.Firestore.FirestoreViewModel
import com.example.noteapp.Firestore.Notes
import com.example.noteapp.databinding.ActivityNoteBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    private val dbHelper by lazy { DatabaseHelper(applicationContext) }
    private val noteDao by lazy { NoteDatabase.getDatabase(this).NoteDao() }
    private val viewModel by lazy { ViewModelProvider(this).get(FirestoreViewModel::class.java) }
    var category = "All"
    var isNoteSelected: Notes? = null
    val db = Firebase.firestore
    val TAG : String = "NoteActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""
        val toolbar = binding.toolbarNote as Toolbar
        setSupportActionBar(toolbar)

        isNoteSelected = intent.extras?.getSerializable("Note") as? Notes

    }

    override fun onStart() {
        super.onStart()
        if (isNoteSelected != null) {
            binding.etTitle.setText(isNoteSelected?.Title)
            binding.etContent.setText(isNoteSelected?.Content)
            category = isNoteSelected!!.Category!!
            binding.listCatgory.setText(category)

        }
    }

    override fun onResume() {
        super.onResume()
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
        var newNote: Notes
        val random = UUID.randomUUID().toString()
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()
        if (title.isEmpty() && content.isEmpty()) {
            finish()
        } else if (title.isEmpty() && content.isNotEmpty()) {
            newNote = Notes(
                random, "", content, category
            )

            saveDB(newNote)
            finish()

        } else if (content.isEmpty() && title.isNotEmpty()) {
            newNote = Notes(
                random, title, "", category
            )
            saveDB(newNote)
            finish()

        } else {
            newNote = Notes(
                random, title, content, category
            )
            saveDB(newNote)
            finish()
        }

    }

    fun updateNote() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()

        if (title.isEmpty() && content.isEmpty()) {
            db.collection("Notes")
                .document(isNoteSelected?.pk!!)
                .delete()
            finish()
        } else {
            val updateNote = Notes(isNoteSelected?.pk, title, content, category)
            db.collection("Notes")
                .document(isNoteSelected?.pk!!)
                .set(updateNote)
            finish()
        }
    }

    fun saveDB(newNote: Notes) {
       viewModel.addNote(newNote)

    }

}