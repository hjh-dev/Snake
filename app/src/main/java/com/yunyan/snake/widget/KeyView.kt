package com.yunyan.snake.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.yunyan.snake.DirectionStateEnum
import com.yunyan.snake.GameStateEnum
import com.yunyan.snake.R

/**
 * @author: YunYan
 * @description: 按键视图： 方向,开始/暂停
 * @date：2021/8/12
 */
class KeyView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet), View.OnClickListener {

    private lateinit var mBtnUp: Button
    private lateinit var mBtnDown: Button
    private lateinit var mBtnLeft: Button
    private lateinit var mBtnRight: Button
    private lateinit var mBtnSwitch: Button

    private var mDirection = DirectionStateEnum.RIGHT
    private var mGameState = GameStateEnum.STOP

    private lateinit var mIKeyData: IKeyData

    init {
        init()
    }

    private fun init() {
        val inflate = inflate(context, R.layout.view_key, this)
        mBtnUp = inflate.findViewById(R.id.keyView_btn_up)
        mBtnDown = inflate.findViewById(R.id.keyView_btn_down)
        mBtnLeft = inflate.findViewById(R.id.keyView_btn_left)
        mBtnRight = inflate.findViewById(R.id.keyView_btn_right)
        mBtnSwitch = inflate.findViewById(R.id.keyView_btn_switch)
        mBtnUp.setOnClickListener(this)
        mBtnDown.setOnClickListener(this)
        mBtnLeft.setOnClickListener(this)
        mBtnRight.setOnClickListener(this)
        mBtnSwitch.setOnClickListener(this)
    }

    fun setIKeyData(iKeyData: IKeyData) {
        this.mIKeyData = iKeyData
    }

    /**
     * 游戏失败
     */
    fun gameOver() {
        mBtnSwitch.setBackgroundResource(R.drawable.select_pause)
        mDirection = DirectionStateEnum.RIGHT
        mGameState = GameStateEnum.STOP
    }

    override fun onClick(v: View?) {
        val id = v?.id
        if (mGameState == GameStateEnum.START) {
            when (id) {
                R.id.keyView_btn_up -> {
                    mDirection = DirectionStateEnum.UP
                }
                R.id.keyView_btn_down -> {
                    mDirection = DirectionStateEnum.DOWN
                }
                R.id.keyView_btn_left -> {
                    mDirection = DirectionStateEnum.LEFT
                }
                R.id.keyView_btn_right -> {
                    mDirection = DirectionStateEnum.RIGHT
                }
            }
        }
        if (id == R.id.keyView_btn_switch) {
            if (mGameState == GameStateEnum.STOP || mGameState == GameStateEnum.PAUSE) {
                mGameState = GameStateEnum.START
                mBtnSwitch.setBackgroundResource(R.drawable.select_start)
            } else {
                mBtnSwitch.setBackgroundResource(R.drawable.select_pause)
                mGameState = GameStateEnum.PAUSE
            }
            //  更新游戏状态
            mIKeyData.gameState(mGameState)
        }
        //  将方向传到背景视图中
        mIKeyData.direction(mDirection)
    }

}