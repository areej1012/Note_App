package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "notes.db",null , 1) {
    private val databaseSQLite : SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
       if (db != null){
           db.execSQL("create table note (Title text , Content text , Category text)")
       }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun saveData(newNote : Note){
        val contentValues = ContentValues()
        contentValues.put("Title", newNote.Title)
        contentValues.put("Content", newNote.Content)
        contentValues.put("Category", newNote.Category)
        databaseSQLite.insert("note", null , contentValues)
    }

}
