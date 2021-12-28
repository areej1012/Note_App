package com.example.noteapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.CardCellBinding
import kotlin.random.Random

class NoteRecycleView(private var notesList : ArrayList<Note> , var context : Context) : RecyclerView.Adapter<NoteRecycleView.HolderItem>(){
    class HolderItem(val binding: CardCellBinding) : RecyclerView.ViewHolder(binding.root)

    private val dbHelper by lazy { DatabaseHelper(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(CardCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
            context.startActivity(intent)
        }

        //for delete
        holder.binding.cardView.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
            contextMenu.add("Delete")
            contextMenu.getItem(0).setOnMenuItemClickListener {
                dbHelper.deleteNote(notesList[position])
                notesList.removeAt(position)
                notifyDataSetChanged()
                return@setOnMenuItemClickListener true

            }
        }
    }
    override fun getItemCount(): Int = notesList.size

    fun update(newList : ArrayList<Note>){
        notesList = newList
        notifyDataSetChanged()
    }
    fun getRandomColor() : Int{
        val colorcode = arrayListOf<Int>()
        colorcode.add (R.color.blue)
        colorcode.add (R.color.yellow)
        colorcode.add (R.color.skyblue)
        colorcode.add (R.color.lightPurple)
        colorcode.add(R.color.lightGreen)
        colorcode.add (R.color.lightBlue)
        colorcode.add (R.color.pink)
        colorcode.add (R.color.red)
        colorcode.add (R.color.greenlight)
        colorcode.add (R.color.notgreen)

        val random = Random
        return colorcode[random.nextInt(colorcode.size)]

    }



}


