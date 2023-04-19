package com.yunyan.snake.utils

import android.content.Context
import android.widget.Toast

/**
 * Toast 工具类
 */
object ToastUtils {

    private var toast: Toast? = null

    fun showToast(context: Context?, text: String?) {
        if (toast != null) {
            toast!!.cancel()
            toast = null
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast!!.show()
    }

}