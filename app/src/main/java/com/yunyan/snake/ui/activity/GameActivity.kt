package com.yunyan.snake.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.yunyan.snake.ui.widget.DirectionStateEnum
import com.yunyan.snake.ui.widget.GameStateEnum
import com.yunyan.snake.R
import com.yunyan.snake.utils.MusicUtils
import com.yunyan.snake.utils.SpUtils
import com.yunyan.snake.ui.widget.BackgroundView
import com.yunyan.snake.ui.widget.IKeyData
import com.yunyan.snake.ui.widget.KeyView

/**
 * 游戏界面
 */
class GameActivity : AppCompatActivity(), IKeyData,
    IScore {

    private lateinit var mKeyView: KeyView
    private lateinit var mBackgroundView: BackgroundView
    private lateinit var mTvScore: TextView
    private lateinit var switchMusic: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initView()
    }

    private fun initView() {
        mKeyView = findViewById(R.id.atyMain_kv)
        mBackgroundView = findViewById(R.id.atyMain_bgv)
        mTvScore = findViewById(R.id.atyGame_tv_score)
        switchMusic = findViewById(R.id.atyMain_switch_music)
        mKeyView.setIKeyData(this)
        mBackgroundView.setIKeyData(this)
        mBackgroundView.setIScore(this)
        setScore(0)
//        playMusic()
    }

    private fun playMusic() {
        val isPlay: Boolean = SpUtils.getInstance(this).getBoolean("isPlay", false)
        switchMusic.isChecked = isPlay
        if (isPlay) {
//            MusicUtils.playSound(this, R.raw.music)
        }
        switchMusic.setOnCheckedChangeListener { buttonView: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
//                MusicUtils.playSound(buttonView.context, R.raw.music)
            } else {
                MusicUtils.release()
            }
            SpUtils.getInstance(buttonView.context).put("isPlay", isChecked)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)
            hideSystemUI()
    }

    /**
     * 隐藏状态栏
     */
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun setScore(score: Int) {
        val s = getString(R.string.game_tv_score, score.toString())
        mTvScore.setText(s)
    }

    override fun gameState(gameState: GameStateEnum) {
        if (gameState == GameStateEnum.STOP) {
            mKeyView.gameOver()
            setScore(0)
        } else {
            mBackgroundView.setGameState(gameState)
        }
    }

    override fun direction(directionState: DirectionStateEnum) {
        mBackgroundView.setDirection(directionState)
    }

    override fun refreshScore(score: Int) {
        setScore(score)
    }

    override fun onPause() {
        MusicUtils.pause()
        super.onPause()
    }

    override fun onStart() {
        MusicUtils.start()
        super.onStart()
    }

    override fun onDestroy() {
        MusicUtils.release()
        super.onDestroy()
    }

}