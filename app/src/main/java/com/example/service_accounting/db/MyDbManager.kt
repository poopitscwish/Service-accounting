package com.example.service_accounting.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class MyDbManager(context: Context) {

    private val myDbHelper = MyDbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToDb(record: Record) {
        val values = ContentValues().apply {
            put(SQL.COLUMN_NAME_FIO_ID, record.FIO_ID)
            put(SQL.COLUMN_NAME_START, record.START)
            put(SQL.COLUMN_NAME_END, record.END)
            put(SQL.COLUMN_NAME_THICK, record.THICK)
            put(SQL.COLUMN_NAME_PRODUCT, record.PRODUCT)
        }
        db?.insert(SQL.RECORDS, null, values)

    }

    fun insertToDb(user: User) {
        val values = ContentValues().apply {
            put(SQL.COLUMN_NAME_FIO, user.fio)
        }
        db?.insert(SQL.USERS, null, values)
    }

    fun updateRecord(record:Record){
        val values = ContentValues()
        values.put(SQL.COLUMN_NAME_FIO_ID, record.FIO_ID)
        values.put(SQL.COLUMN_NAME_START, record.START)
        values.put(SQL.COLUMN_NAME_END, record.END)
        values.put(SQL.COLUMN_NAME_THICK, record.THICK)
        values.put(SQL.COLUMN_NAME_PRODUCT, record.PRODUCT)
        val result = db?.update(SQL.RECORDS, values, "${SQL.COLUMN_NAME_RECORD_ID}=?", arrayOf((record.ID + 1).toString()))
        println("result=$result")
    }
    fun deleteRecord(id:Int){
        val values = ContentValues()
        val result = db?.delete(SQL.RECORDS, "${SQL.COLUMN_NAME_RECORD_ID}=?", arrayOf((id + 1).toString()))
        println("result=$result")
    }

    fun readUsers(): MutableList<User> {
        val dataList = mutableListOf<User>()
        val cursor = db?.query(
            SQL.USERS,
            null,
            null,
            null,
            null,
            null,
            null
        )
        while (cursor?.moveToNext()!!) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_ID))
            val userFio = cursor.getString(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_FIO))
            val user = User(userId,userFio)
            dataList.add(user)
        }
        cursor.close()
        return dataList
    }
    fun readRecords():MutableList<Record>{
        val datalist = mutableListOf<Record>()
        val cursor = db?.query(
            SQL.RECORDS,
            null,
            null,
            null,
            null,
            null,
            null
        )
        while(cursor?.moveToNext()!!){
            val recordID = cursor.getInt(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_RECORD_ID))
            val recrodFIO = cursor.getInt(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_FIO_ID))
            val recordStart = cursor.getString(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_START))
            val recordEnd =  cursor.getString(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_END))
            val recordThick = cursor.getInt(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_THICK))
            val recordProduct = cursor.getString(cursor.getColumnIndexOrThrow(SQL.COLUMN_NAME_PRODUCT))
            val record = Record(recordID,recrodFIO,recordStart,recordEnd,recordThick,recordProduct)
            datalist.add(record)
        }
        cursor.close()
        return datalist
    }

    fun closeDb() {
        myDbHelper.close()
    }

}