package com.example.tic_tac_toe_kotlin_firebase

import kotlin.random.Random

data  class GameModel (
    var gameId : String = "-1",
    var filledPos : MutableList<String> = mutableListOf("","","","","","","","",""),
    var winner : String = "",
    var gameStatus : GameStatus = GameStatus.CREATED,
    var curentPlayer : String = (arrayOf("X","O"))[Random.nextInt(until = 2)]
)

enum class GameStatus{
    CREATED,
    JOINED,
    INPROGRES,
    FINISHED
}