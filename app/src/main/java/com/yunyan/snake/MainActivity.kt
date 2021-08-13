package com.yunyan.snake

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yunyan.snake.widget.BackgroundView
import com.yunyan.snake.widget.IKeyData
import com.yunyan.snake.widget.KeyView

class MainActivity : AppCompatActivity(), IKeyData {

    private lateinit var mKeyView: KeyView
    private lateinit var mBackgroundView: BackgroundView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mKeyView = findViewById(R.id.controlView)
        mBackgroundView = findViewById(R.id.backgroundView)
        mKeyView.setIKeyData(this)
        mBackgroundView.setIKeyData(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    override fun gameState(gameState: GameStateEnum) {
        if (gameState == GameStateEnum.STOP) {
            mKeyView.gameOver()
        } else {
            mBackgroundView.setGameState(gameState)
        }
    }

    override fun direction(directionState: DirectionStateEnum) {
        mBackgroundView.setDirection(directionState)
    }
}