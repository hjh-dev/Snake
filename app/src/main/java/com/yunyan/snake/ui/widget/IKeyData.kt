package com.yunyan.snake.ui.widget

/**
 * @author: YunYan
 * @description: 按键数据提供接口
 * @date：2021/8/12
 */
interface IKeyData {

    /**
     * 获取游戏状态
     */
    fun gameState(gameState: GameStateEnum)

    /**
     * 获取蛇头方向
     */
    fun direction(directionState: DirectionStateEnum)

}