package com.samsul.crudsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        val DATABASE_NAME = "stars.db"
        val TABLE_NAME = "star_table"
        val COL_1 = "ID"
        val COL_2 = "NAME"
        val COL_3 = "GALAXY"
        val COL_4 = "TYPE"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("Create Table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, GALAXY TEXT, TYPE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun updateData(id : String, name : String, surname : String, marks : String) : Boolean {
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(COL_1, id)
        contentValue.put(COL_2, name)
        contentValue.put(COL_3, surname)
        contentValue.put(COL_4, marks)
        db.update(TABLE_NAME, contentValue, "ID=?", arrayOf(id))


        return true
    }
    fun insertData(name : String, surname: String, marks: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2,name)
        contentValues.put(COL_3,surname)
        contentValues.put(COL_4,marks)
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun deleteData(id : String) : Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID=?", arrayOf(id))
    }


    val allData : Cursor
    get() {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        return res
    }
}