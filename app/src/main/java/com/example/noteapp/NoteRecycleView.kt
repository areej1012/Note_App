package com.example.noteapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.DB.DatabaseHelper
import com.example.noteapp.DB.MyViewModel
import com.example.noteapp.DB.NoteDatabase
import com.example.noteapp.Firestore.FirestoreViewModel
import com.example.noteapp.Firestore.Notes
import com.example.noteapp.databinding.CardCellBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class NoteRecycleView(private var notesList: ArrayList<Notes>, var context: Context) :
    RecyclerView.Adapter<NoteRecycleView.HolderItem>() {
    class HolderItem(val binding: CardCellBinding) : RecyclerView.ViewHolder(binding.root)

    private val dbHelper by lazy { DatabaseHelper(context) }
    private val noteDao by lazy { NoteDatabase.getDatabase(context).NoteDao() }
  //  private val viewModel by lazy { ViewModelProvider(context).get(FirestoreViewModel::class.java) }
    private val db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            CardCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        //change color
        holder.binding.cardView.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                getRandomColor(),
                null
            )
        )

        holder.binding.apply {
            tvTitle.text = notesList[position].Title
            tvContent.text = notesList[position].Content
        }
        //open Update Activity
        holder.binding.cardView.setOnClickListener {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("Note", notesList[position])
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        //for delete
        holder.binding.cardView.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
            contextMenu.add("Delete")
            contextMenu.getItem(0).setOnMenuItemClickListener {
            db.collection("Notes")
                .document(notesList[position].pk!!)
                .delete()
                notesList.removeAt(position)
                return@setOnMenuItemClickListener true

            }
        }
    }

    override fun getItemCount(): Int = notesList.size

    fun update(newList: List<Notes>) {
        notesList = newList as ArrayList<Notes>
        notifyDataSetChanged()
        for (notes in notesList){
            Log.e("title R", notes.Title.toString())
        }
    }

    fun getRandomColor(): Int {
        val colorcode = arrayListOf<Int>()
        colorcode.add(R.color.blue)
        colorcode.add(R.color.yellow)
        colorcode.add(R.color.skyblue)
        colorcode.add(R.color.lightPurple)
        colorcode.add(R.color.lightGreen)
        colorcode.add(R.color.lightBlue)
        colorcode.add(R.color.pink)
        colorcode.add(R.color.red)
        colorcode.add(R.color.greenlight)
        colorcode.add(R.color.notgreen)

        val random = Random
        return colorcode[random.nextInt(colorcode.size)]

    }


}


