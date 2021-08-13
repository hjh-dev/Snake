package com.yunyan.snake.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.yunyan.snake.DirectionStateEnum
import com.yunyan.snake.GameStateEnum
import com.yunyan.snake.R
import java.util.*

/**
 * @author: YunYan
 * @description: 贪吃蛇背景
 * @date：2021/8/12
 */
class BackgroundView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private lateinit var mPaintHead: Paint
    private lateinit var mPaintBody: Paint
    private lateinit var mPaintFood: Paint

    /**
     * 蛇整体长度
     */
    private var mSnakeLength = 2

    private val mSnakeX = FloatArray(800)
    private val mSnakeY = FloatArray(800)

    private var mDirectionEnum = DirectionStateEnum.RIGHT
    private var mGameState = GameStateEnum.STOP

    private lateinit var mIKeyData: IKeyData

    private var mFoodX = 0f
    private var mFoodY = 0f

    private val mHandler = @SuppressLint("HandlerLeak")

    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 99 && mGameState == GameStateEnum.START) {
                judgmentDirection()
                invalidate()
            }
        }

    }

    /**
     * 定时器，每隔 0.1s向 handler 发送消息
     */
    private val mTimer = Timer().schedule(object : TimerTask() {
        override fun run() {
            val message = Message()
            message.what = 99
            mHandler.sendMessage(message)
        }

    }, 0, 100)

    init {
        init()
    }

    private fun init() {
        mPaintHead = Paint()
        mPaintBody = Paint()
        mPaintFood = Paint()
        mPaintHead.isAntiAlias = true
        mPaintBody.isAntiAlias = true
        mPaintFood.isAntiAlias = true
        mPaintHead.color = resources.getColor(R.color.head, null)
        mPaintBody.color = resources.getColor(R.color.body, null)
        mPaintFood.color = resources.getColor(R.color.food, null)
        mSnakeX[0] = 750f
        mSnakeY[0] = 500f
        mFoodX = 25 * Random().nextInt(15).toFloat()
        mFoodY = 25 * Random().nextInt(15).toFloat()
    }

    fun setIKeyData(iKeyData: IKeyData) {
        this.mIKeyData = iKeyData
    }

    fun setGameState(gameStateEnum: GameStateEnum) {
        this.mGameState = gameStateEnum
    }

    /**
     * 游戏失败
     */
    private fun gameOver() {
        mGameState = GameStateEnum.STOP
        mIKeyData.gameState(mGameState)
        mSnakeLength = 2
        mSnakeX[0] = 750f
        mSnakeY[0] = 500f
    }

    /**
     * 设置蛇头方向
     */
    fun setDirection(directionState: DirectionStateEnum) {
        if (isDirectionContrary(directionState)) {
            mGameState = GameStateEnum.STOP
            gameOver()
            Toast.makeText(context, "方向相反，游戏失败！", Toast.LENGTH_SHORT).show()
        }
        if (mGameState == GameStateEnum.START) {
            this.mDirectionEnum = directionState
        }
    }

    /**
     * 判断按键方向是否与所前进方向相反
     */
    private fun isDirectionContrary(directionState: DirectionStateEnum): Boolean {
        when (directionState) {
            DirectionStateEnum.UP -> {
                if (this.mDirectionEnum == DirectionStateEnum.DOWN) return true
            }
            DirectionStateEnum.DOWN -> {
                if (this.mDirectionEnum == DirectionStateEnum.UP) return true
            }
            DirectionStateEnum.LEFT -> {
                if (this.mDirectionEnum == DirectionStateEnum.RIGHT) return true
            }
            DirectionStateEnum.RIGHT -> {
                if (this.mDirectionEnum == DirectionStateEnum.LEFT) return true
            }
        }
        return false
    }

    /**
     * 判断蛇头方向
     */
    private fun judgmentDirection() {
        when (mDirectionEnum) {
            DirectionStateEnum.UP -> {
                mSnakeY[0] = mSnakeY[0] - 25
                if (mSnakeY[0] < 25) mSnakeY[0] = measuredHeight.toFloat()
            }
            DirectionStateEnum.DOWN -> {
                mSnakeY[0] = mSnakeY[0] + 25
                if (mSnakeY[0] > measuredHeight) mSnakeY[0] = 25f
            }
            DirectionStateEnum.LEFT -> {
                mSnakeX[0] = mSnakeX[0] - 25
                if (mSnakeX[0] < 25) mSnakeX[0] = measuredWidth.toFloat()
            }
            DirectionStateEnum.RIGHT -> {
                mSnakeX[0] = mSnakeX[0] + 25
                if (mSnakeX[0] > measuredWidth) mSnakeX[0] = 25f
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (mGameState == GameStateEnum.START) {
            //  判断是否吃到食物
            if (mSnakeX[0] == mFoodX && mSnakeY[0] == mFoodY) {
                mFoodX = 25 * Random().nextInt(measuredWidth / 25).toFloat()
                mFoodY = 25 * Random().nextInt(measuredHeight / 25).toFloat()
                mSnakeLength++
            }

            //  绘制蛇身
            for (i in mSnakeLength downTo 1) {

                if (mSnakeX[0] == mSnakeX[i] && mSnakeY[0] == mSnakeY[i]) {
                    Toast.makeText(context, "咬到自己，游戏失败！", Toast.LENGTH_SHORT).show()
                    gameOver()
                }
                mSnakeX[i] = mSnakeX[i - 1]
                mSnakeY[i] = mSnakeY[i - 1]
                canvas?.drawOval(
                    mSnakeX[i],
                    mSnakeY[i],
                    mSnakeX[i] + 25,
                    mSnakeY[i] + 25,
                    mPaintBody
                )
            }
        } else {
            canvas?.drawOval(
                mSnakeX[0],
                mSnakeY[0],
                mSnakeX[0] - 25,
                mSnakeY[0] + 25,
                mPaintBody
            )
        }
        //  绘制蛇头
        canvas?.drawRect(mSnakeX[0], mSnakeY[0], mSnakeX[0] + 25, mSnakeY[0] + 25, mPaintHead)
        //  绘制食物
        canvas?.drawOval(mFoodX, mFoodY, mFoodX + 25, mFoodY + 25, mPaintFood)
    }
}