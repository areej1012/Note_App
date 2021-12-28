package com.example.noteapp

import java.io.Serializable

class Note(var pk : Int? ,var Title : String, var Content : String, var Category: String) : Serializable {
}