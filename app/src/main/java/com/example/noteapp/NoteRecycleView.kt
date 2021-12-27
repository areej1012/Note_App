package com.example.noteapp

import android.graphics.Color
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.CardCellBinding
import kotlin.random.Random

class NoteRecycleView(private var notesList : ArrayList<Note>) : RecyclerView.Adapter<NoteRecycleView.HolderItem>() {
    class HolderItem(val binding: CardCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(CardCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        holder.binding.cardView.setCardBackgroundColor(holder.itemView.resources.getColor(getRandomColor(), null) )
        holder.binding.apply {
            tvTitle.text = notesList[position].Title
            tvContent.text = notesList[position].Content

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
        colorcode.add (R.color.lightPurple);
        colorcode.add(R.color.lightGreen);
        colorcode.add (R.color.lightBlue) ;
        colorcode.add (R.color.pink);
        colorcode.add (R.color.red) ;
        colorcode.add (R.color.greenlight);
        colorcode.add (R.color.notgreen);

        var random = Random
        return colorcode[random.nextInt(colorcode.size)]

    }
}


