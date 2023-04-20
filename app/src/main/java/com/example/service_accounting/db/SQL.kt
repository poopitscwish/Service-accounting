package com.example.service_accounting.db

import android.provider.BaseColumns

object SQL : BaseColumns {
    const val USERS = "users"

    const val COLUMN_NAME_FIO = "FIO"
    const val COLUMN_NAME_ID = "ID"

    const val CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS $USERS (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_FIO TEXT)"


    const val RECORDS = "records"
    const val COLUMN_NAME_RECORD_ID = "ID"
    const val COLUMN_NAME_FIO_ID = "FIO_ID"
    const val COLUMN_NAME_START = "START"
    const val COLUMN_NAME_END = "END"
    const val COLUMN_NAME_THICK = "THICK"
    const val COLUMN_NAME_PRODUCT = "PRODUCT"

    const val DATABASE_VERSION = 4
    const val DATABASE_NAME = "Service.db"

    const val CREATE_TABLE_RECORDS =
        "CREATE TABLE IF NOT EXISTS $RECORDS (" +
                "$COLUMN_NAME_RECORD_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_NAME_FIO_ID INTEGER, " +
                "$COLUMN_NAME_START TEXT," +
                "$COLUMN_NAME_END TEXT," +
                "$COLUMN_NAME_THICK INTEGER," +
                "$COLUMN_NAME_PRODUCT TEXT," +
                "FOREIGN KEY ($COLUMN_NAME_FIO_ID) REFERENCES $USERS($COLUMN_NAME_ID))"

    const val SQL_DELETE_ENTRIES_USERS = "DROP TABLE IF EXISTS $USERS"
    const val SQL_DELETE_ENTRIES_RECORDS = "DROP TABLE IF EXISTS $USERS"
}