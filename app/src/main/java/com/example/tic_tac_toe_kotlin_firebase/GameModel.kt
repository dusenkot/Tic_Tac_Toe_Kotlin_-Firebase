package com.example.tic_tac_toe_kotlin_firebase

import kotlin.random.Random

data class GameModel (
    var gameId : String = "-1",
    var filledPos : MutableList<String> = mutableListOf("","","","","","","","",""),
    var filledImgPos : MutableList<Int> = MutableList(9) { R.color.transparent },
    var winner : String ="",
    var gameStatus : GameStatus = GameStatus.CREATED,
    var currentPlayer : String = (arrayOf("X","O"))[Random.nextInt(2)]
)
{
}

enum class GameStatus{
    CREATED,
    JOINED,
    INPROGRESS,
    FINISHED
}