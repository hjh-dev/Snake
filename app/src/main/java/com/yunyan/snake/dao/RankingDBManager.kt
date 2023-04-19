package com.yunyan.snake.dao

import android.content.Context

/**
 * 数据库工具类
 */
class RankingDBManager private constructor() {

    companion object {
        private val instance = RankingDBManager()
        private var mDB: MyDBOpenHelper? = null
        fun getInstance(context: Context?): RankingDBManager {
            if (mDB == null) {
                mDB = MyDBOpenHelper(context)
            }
            return instance
        }
    }

    /**
     * 插入数据
     */
    fun insert(userDao: UserDao) {
        val db = mDB!!.writableDatabase
        val sql = "INSERT INTO ranking(name,score) VALUES(?,?)"
        db.execSQL(
            sql, arrayOf<Any>(userDao.name, userDao.score)
        )
        db.close()
    }

    /**
     * 查询数据
     */
    fun query(): List<UserDao> {
        val db = mDB!!.readableDatabase
        // 进行数据查询并按照分数降序排序
        val sql = "SELECT * FROM ranking ORDER BY score DESC"
        val cursor = db.rawQuery(sql, null)
        val list: MutableList<UserDao> = ArrayList()
        if (cursor.count > 0) {
            cursor.moveToFirst()
            for (i in 0 until cursor.count) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val score = cursor.getInt(cursor.getColumnIndexOrThrow("score"))
                list.add(UserDao(name, score))
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return list
    }

}