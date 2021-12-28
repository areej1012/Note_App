package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "notes.db",null , 2) {
    private val databaseSQLite : SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table note (pk INTEGER PRIMARY KEY AUTOINCREMENT, Title text , Content text , Category text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS note")
        onCreate(db)
    }

    fun saveData(newNote : Note){
        val contentValues = ContentValues()
        contentValues.put("Title", newNote.Title)
        contentValues.put("Content", newNote.Content)
        contentValues.put("Category", newNote.Category)
        databaseSQLite.insert("note", null , contentValues)
    }

    fun getData() : ArrayList<Note>{
        val noteList = arrayListOf<Note>()
        val cursor : Cursor = databaseSQLite.rawQuery("SELECT * FROM note", null)
        if(cursor.count < 1){
            Log.e("DB read", "ERROR")
        }
        else {
            while (cursor.moveToNext()){
                val pk = cursor.getInt(0)
                val title = cursor.getString(1)
                val content = cursor.getString(2)
                val category = cursor.getString(3)
                noteList.add(Note(pk, title , content, category))
            }
        }
        return noteList
    }
    fun updateDate(note : Note){
        val contentValues = ContentValues()
        contentValues.put("Title", note.Title)
        contentValues.put("Content", note.Content)
        contentValues.put("Category", note.Category)
        databaseSQLite.update("note",contentValues, "pk = ${note.pk}", null)
    }
    fun deleteNote(note : Note){
        databaseSQLite.delete("note","pk = ${note.pk}", null)
    }

}
