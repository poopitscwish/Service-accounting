package com.example.service_accounting.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context):SQLiteOpenHelper(
    context,
    SQL.DATABASE_NAME,
    null,
    SQL.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL.CREATE_TABLE_USERS)
        db?.execSQL(SQL.CREATE_TABLE_RECORDS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL.SQL_DELETE_ENTRIES_USERS)
        db?.execSQL(SQL.SQL_DELETE_ENTRIES_RECORDS)
        onCreate(db)
    }
}
