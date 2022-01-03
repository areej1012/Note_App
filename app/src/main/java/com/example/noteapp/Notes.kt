package com.example.noteapp

import androidx.room.PrimaryKey
import java.io.Serializable

data class Notes( var pk: String? = null,
var Title: String? = "",
var Content: String? = "",
var Category: String? = ""
) : Serializable {
}
