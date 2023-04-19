package com.yunyan.snake.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences 工具类
 */
class SpUtils private constructor() {

    companion object {
        private const val SP = "data"
        private val instance = SpUtils()
        private var mSp: SharedPreferences? = null
        fun getInstance(context: Context): SpUtils {
            if (mSp == null) {
                mSp = context.getSharedPreferences(SP, Context.MODE_PRIVATE)
            }
            return instance
        }
    }

    fun put(key: String?, value: Any?) {
        val edit = mSp!!.edit()
        if (value is String) {
            edit.putString(key, value as String?)
        } else if (value is Boolean) {
            edit.putBoolean(key, (value as Boolean?)!!)
        } else if (value is Int) {
            edit.putInt(key, (value as Int?)!!)
        }
        edit.apply()
    }

    fun getString(key: String?, defValue: String?): String? {
        return mSp!!.getString(key, defValue)
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return mSp!!.getBoolean(key, defValue)
    }

    fun getInt(key: String?, defValue: Int): Int {
        return mSp!!.getInt(key, defValue)
    }

}