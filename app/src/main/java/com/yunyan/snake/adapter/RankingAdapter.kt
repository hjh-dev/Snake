package com.yunyan.snake.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yunyan.snake.R
import com.yunyan.snake.dao.UserDao

/**
 * 排行榜 RecyclerView 适配器
 */
class RankingAdapter(mList: List<UserDao>) : RecyclerView.Adapter<RankingAdapter.ViewHolder?>() {

    private val mList: List<UserDao>

    init {
        this.mList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_ranking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = mList[position].name
        holder.tvScore.text = mList[position].score.toString()
        val score = position + 1
        holder.tvRanking.text = score.toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName: TextView
        val tvScore: TextView
        val tvRanking: TextView

        init {
            tvName = view.findViewById<View>(R.id.itemRv_ranking_name) as TextView
            tvScore = view.findViewById<View>(R.id.itemRv_ranking_score) as TextView
            tvRanking = view.findViewById<View>(R.id.itemRv_ranking_ranking) as TextView
        }
    }
}