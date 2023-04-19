package com.yunyan.snake.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunyan.snake.R
import com.yunyan.snake.adapter.RankingAdapter
import com.yunyan.snake.dao.RankingDBManager
import com.yunyan.snake.dao.UserDao

/**
 * 排行榜界面
 */
class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        val ranking = findViewById<RecyclerView>(R.id.atyRanking_rv)
        val list: List<UserDao> = RankingDBManager.getInstance(this).query()
        val rankingAdapter = RankingAdapter(list)
        ranking.layoutManager = LinearLayoutManager(this)
        ranking.adapter = rankingAdapter
    }
}