package com.yunyan.snake.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.yunyan.snake.R

/**
 * 主界面
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.atyMain_btn_start)
        val btnRanking = findViewById<Button>(R.id.atyMain_btn_ranking)

        btnStart.setOnClickListener { v: View? ->
            toActivity(
                GameActivity::class.java
            )
        }
        btnRanking.setOnClickListener { v: View? ->
            toActivity(
                RankingActivity::class.java
            )
        }

    }

    private fun toActivity(cls: Class<out AppCompatActivity?>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

}