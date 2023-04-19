package com.yunyan.snake.utils

import android.content.Context
import android.media.MediaPlayer

/**
 * 音乐播放工具类
 */
object MusicUtils {

    private var mPlayer: MediaPlayer? = null
    private var isPause = false

    fun playSound(context: Context?, rawResId: Int) {
        release()
        mPlayer = MediaPlayer.create(context, rawResId)
        mPlayer!!.start()
    }

    fun pause() {
        if (mPlayer != null && mPlayer!!.isPlaying) {
            mPlayer!!.pause()
            isPause = true
        }
    }

    fun start() {
        if (mPlayer != null && isPause) {
            mPlayer!!.start()
            isPause = false
        }
    }

    fun release() {
        if (mPlayer != null) {
            mPlayer!!.release()
            mPlayer = null
        }
    }
}