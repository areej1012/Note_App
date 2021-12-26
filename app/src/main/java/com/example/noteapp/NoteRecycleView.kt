package com.example.noteapp

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.CardCellBinding

class NoteRecycleView(private var notesList : ArrayList<Note>) : RecyclerView.Adapter<NoteRecycleView.HolderItem>() {
    class HolderItem(val binding: CardCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(CardCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        holder.binding.apply {
            tvTitle.text = notesList[position].Title
            tvContent.text = notesList[position].Content
        }
    }

    override fun getItemCount(): Int = notesList.size
}


