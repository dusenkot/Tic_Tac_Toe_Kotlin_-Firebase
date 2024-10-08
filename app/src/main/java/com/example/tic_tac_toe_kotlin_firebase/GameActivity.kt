package com.example.tic_tac_toe_kotlin_firebase

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tic_tac_toe_kotlin_firebase.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityGameBinding

    private var gameModel: GameModel? = null
    private lateinit var scaleAnimation: Animation
    private lateinit var scaleAnimationDown: Animation
    private lateinit var scaleAnimationJupiter: Animation
    private lateinit var scaleAnimationDownJupiter: Animation

    var X_Winner_Score = 0
    var O_Winner_Score = 0
    private var hasScoreUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleAnimationDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        scaleAnimationJupiter = AnimationUtils.loadAnimation(this, R.anim.scale_up_jupiter)
        scaleAnimationDownJupiter = AnimationUtils.loadAnimation(this, R.anim.scale_down_jupiter)
        GameData.fetchGameModel()

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)

        binding.startGameBtn.setOnClickListener {
            startGame()
            onstartGameBtn()

        }
        resetScore()
        GameData.gameModel.observe(this) {
            gameModel = it
            setUI()
        }
    }

    private fun onstartGameBtn() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.button_scale)
        binding.btnLayout.startAnimation(animation)
    }

    fun setUI() {
        gameModel?.apply {
            binding.btn0.text = filledPos[0]
            binding.btn1.text = filledPos[1]
            binding.btn2.text = filledPos[2]
            binding.btn3.text = filledPos[3]
            binding.btn4.text = filledPos[4]
            binding.btn5.text = filledPos[5]
            binding.btn6.text = filledPos[6]
            binding.btn7.text = filledPos[7]
            binding.btn8.text = filledPos[8]
            binding.img0.setBackgroundResource(filledImgPos[0])
            binding.img1.setBackgroundResource(filledImgPos[1])
            binding.img2.setBackgroundResource(filledImgPos[2])
            binding.img3.setBackgroundResource(filledImgPos[3])
            binding.img4.setBackgroundResource(filledImgPos[4])
            binding.img5.setBackgroundResource(filledImgPos[5])
            binding.img6.setBackgroundResource(filledImgPos[6])
            binding.img7.setBackgroundResource(filledImgPos[7])
            binding.img8.setBackgroundResource(filledImgPos[8])
            binding.startGameBtn.visibility = View.VISIBLE

            binding.gameStatusTex.text =
                when (gameStatus) {
                    GameStatus.CREATED -> {

                        binding.startGameBtn.visibility = View.INVISIBLE
                        "Game ID :" + gameId
                    }
                    GameStatus.JOINED -> {

                        "Click on start game"
                    }
                    GameStatus.INPROGRESS -> {
                        binding.startGameBtn.visibility = View.INVISIBLE
                        when (GameData.myID) {

                            currentPlayer -> "Your turn"
                            else -> currentPlayer + " turn"

                        }
                    }
                    GameStatus.FINISHED -> {
                        if (winner.isNotEmpty()) {
                            if (!hasScoreUpdated) {
                                hasScoreUpdated = true
                                if (winner == "O") {
                                    O_Winner_Score += 1
                                    binding.OText.text = O_Winner_Score.toString()
                                } else {
                                    X_Winner_Score += 1
                                    binding.XText.text = X_Winner_Score.toString()
                                }
                            }
                            when (GameData.myID) {
                                winner -> "You won"
                                else -> "$winner Won"
                            }
                        } else {
                            "DRAW"
                        }

                    }
                }
            updateCurrentPlayerAnimation(currentPlayer)
        }
    }

    private fun startGame() {
        gameModel?.apply {
            hasScoreUpdated = false
            updateGameData(
                GameModel(
                    gameId = gameId,

                    gameStatus = GameStatus.INPROGRESS
                )
            )
            updateCurrentPlayerAnimation(currentPlayer)
        }
    }

    private fun updateGameData(model: GameModel) {
        hasScoreUpdated = false
        GameData.saveGameModel(model)
    }

    private fun checkForWinner() {
        val winningPos = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6),
        )

        gameModel?.apply {
            for (i in winningPos) {
                if (
                    filledPos[i[0]] == filledPos[i[1]] &&
                    filledPos[i[1]] == filledPos[i[2]] &&
                    filledPos[i[0]].isNotEmpty()
                ) {

                    gameStatus = GameStatus.FINISHED

                    winner = filledPos[i[0]]

                }
            }

            if (filledPos.none { it.isEmpty() }) {

                gameStatus = GameStatus.FINISHED

            }
            resetScore()

        }
    }

    override fun onClick(v: View?) {
        gameModel?.apply {
            if (gameStatus != GameStatus.INPROGRESS) {
                Toast.makeText(applicationContext, "Game not started", Toast.LENGTH_SHORT).show()

                return
            }

            if (gameId != "-1" && currentPlayer != GameData.myID) {
                Toast.makeText(applicationContext, "Not your turn", Toast.LENGTH_SHORT).show()

                return
            }

            val clickedPos = (v?.tag as String).toInt()
            if (filledPos[clickedPos].isEmpty()) {
                filledPos[clickedPos] = currentPlayer
                filledImgPos[clickedPos] = if (currentPlayer == "X") R.drawable.starkszyzuwek else R.drawable.jupiterkuwko

                checkForWinner()

                currentPlayer = if (currentPlayer == "X") "O" else "X"

                updateCurrentPlayerAnimation(currentPlayer)

                updateGameData(this)

            }
        }
    }

    private fun updateCurrentPlayerAnimation(currentPlayer: String) {
        if (currentPlayer == "X") {
            binding.star.setBackgroundResource(R.drawable.star)
            binding.star.startAnimation(scaleAnimation)

            scaleAnimationDownJupiter.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    binding.jupiter.setBackgroundResource(0)
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            binding.jupiter.startAnimation(scaleAnimationDownJupiter)
        } else if (currentPlayer == "O") {
            binding.jupiter.setBackgroundResource(R.drawable.jupiter)
            binding.jupiter.startAnimation(scaleAnimationJupiter)

            scaleAnimationDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    binding.star.setBackgroundResource(0)
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            binding.star.startAnimation(scaleAnimationDown)
        }
    }
    private fun resetScore() {
        binding.btnRestart.setOnClickListener {
            binding.XText.text = "0"
            binding.OText.text = "0"
            X_Winner_Score = 0
            O_Winner_Score = 0


        }
    }
}



