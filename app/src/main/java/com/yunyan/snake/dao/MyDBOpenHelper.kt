package com.yunyan.snake.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * 数据库帮助类
 */
class MyDBOpenHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "snake.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql =
            "CREATE TABLE ranking(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,name VARCHAR(20) NOT NULL,score INTEGER NOT NULL)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

}