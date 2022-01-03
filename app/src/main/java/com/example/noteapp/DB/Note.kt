package com.example.noteapp.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) var pk: Int? = null,
    var Title: String,
    var Content: String,
    var Category: String
) : Serializable {
}