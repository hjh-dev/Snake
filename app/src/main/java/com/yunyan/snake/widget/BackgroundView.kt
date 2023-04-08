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
     * 默认蛇身长度
     */
    private val DEFAULT_LENGTH = 2

    /**
     * 蛇整体长度
     */
    private var mSnakeLength = DEFAULT_LENGTH

    private lateinit var mSnakeX: FloatArray
    private lateinit var mSnakeY: FloatArray

    private var mDirectionEnum = DirectionStateEnum.RIGHT
    private var mGameState = GameStateEnum.STOP

    private lateinit var mIKeyData: IKeyData

    private var mFoodX = 0f
    private var mFoodY = 0f

    init {
        init()
    }

    private fun init() {
        initPaint()
        initData()
        moveSnake()
    }

    /**
     * 初始化画笔
     */
    private fun initPaint() {
        mPaintHead = Paint()
        mPaintBody = Paint()
        mPaintFood = Paint()
        mPaintHead.isAntiAlias = true
        mPaintBody.isAntiAlias = true
        mPaintFood.isAntiAlias = true
        mPaintHead.color = resources.getColor(R.color.head, null)
        mPaintBody.color = resources.getColor(R.color.body, null)
        mPaintFood.color = resources.getColor(R.color.food, null)
    }

    /**
     * 初始化蛇与食物
     */
    private fun initData() {
        mSnakeX = FloatArray(800)
        mSnakeY = FloatArray(800)
        mSnakeX[0] = 750f
        mSnakeY[0] = 500f
        mFoodX = 25 * Random().nextInt(15).toFloat()
        mFoodY = 25 * Random().nextInt(15).toFloat()
    }

    /**
     * 移动蛇
     */
    private fun moveSnake() {
        @SuppressLint("HandlerLeak") val mHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == 99 && mGameState === GameStateEnum.START) {
                    judgmentDirection()
                    invalidate()
                }
            }
        }
        // 定时器，每隔 0.1s向 handler 发送消息
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val message = Message()
                message.what = 99
                mHandler.sendMessage(message)
            }
        }, 0, 100)
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
        mDirectionEnum = DirectionStateEnum.RIGHT
        mIKeyData.gameState(mGameState)
        mSnakeLength = DEFAULT_LENGTH
        mSnakeX[0] = 750f
        mSnakeY[0] = 500f
        initData()
        invalidate()
    }

    /**
     * 设置蛇头方向
     */
    fun setDirection(directionState: DirectionStateEnum) {
        if (isDirectionContrary(directionState)) {
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
                // 超过屏幕上侧，从下恻出
                mSnakeY[0] = mSnakeY[0] - 25
                if (mSnakeY[1] <= 0) mSnakeY[0] = measuredHeight.toFloat()
            }
            DirectionStateEnum.DOWN -> {
                // 超过屏幕下侧，从上恻出
                mSnakeY[0] = mSnakeY[0] + 25
                if (mSnakeY[0] > measuredHeight) mSnakeY[0] = 0f
            }
            DirectionStateEnum.LEFT -> {
                // 超过屏幕左侧，从右恻出
                mSnakeX[0] = mSnakeX[0] - 25
                if (mSnakeX[1] <= 0) mSnakeX[0] = measuredWidth.toFloat()
            }
            DirectionStateEnum.RIGHT -> {
                // 超过屏幕右侧，从左恻出
                mSnakeX[0] = mSnakeX[0] + 25
                if (mSnakeX[0] > measuredWidth) mSnakeX[0] = 0f
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (mGameState == GameStateEnum.START) {
            //  判断是否吃到食物
            if ((mSnakeX[0] in mFoodX - 15..mFoodX + 15) && (mSnakeY[0] in mFoodY - 15..mFoodY + 15)) {
                mFoodX = 25 * Random().nextInt(measuredWidth / 25).toFloat()
                mFoodY = 25 * Random().nextInt(measuredHeight / 25).toFloat()
                mSnakeLength++
            }
            //  绘制蛇身
            for (i in mSnakeLength downTo 1) {
                if (mSnakeX[0] == mSnakeX[i] && mSnakeY[0] == mSnakeY[i]) {
                    gameOver()
                    Toast.makeText(context, "咬到自己，游戏失败！", Toast.LENGTH_SHORT).show()
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