package com.example.tic_tac_toe_kotlin_firebase

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tic_tac_toe_kotlin_firebase.databinding.ActivityGameBinding
import com.example.tic_tac_toe_kotlin_firebase.databinding.ActivityMainBinding

class GameActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGameBinding

    private  var gameModel : GameModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        }
        GameData.gameModel.observe(this){
            gameModel = it
            setUI()
        }

    }


    fun setUI(){
        gameModel?.apply{
            binding.btn0.text = filledPos[0]
            binding.btn1.text = filledPos[1]
            binding.btn2.text = filledPos[2]
            binding.btn3.text = filledPos[3]
            binding.btn4.text = filledPos[4]
            binding.btn5.text = filledPos[5]
            binding.btn6.text = filledPos[6]
            binding.btn7.text = filledPos[7]
            binding.btn8.text = filledPos[8]

            binding.gameStatusTex.text =
                when(gameStatus){
                    GameStatus.CREATED ->{
                        "Game ID: " + gameId
                    }
                    GameStatus.JOINED ->{
                        "Click on start Game"
                    }
                    GameStatus.INPROGRES ->{
                        curentPlayer + " turn"
                    }
                    GameStatus.FINISHED ->{
                        if(winner.isNotEmpty()) winner + " Won"
                        else "Draw"
                    }
                }

        }

    }
    fun startGame(){
        gameModel?.apply {
        updateGameData(
            GameModel(
                gameId = gameId,
                gameStatus = GameStatus.INPROGRES
            )
        )
        }
    }
    fun updateGameData(model: GameModel){
        GameData.saveGameModel(model)
    }
    override fun onClick(v: View?) {
        gameModel?.apply {
            if(gameStatus != GameStatus.INPROGRES){
                Toast.makeText(applicationContext,"Game not started",Toast.LENGTH_SHORT).show()
                return
            }
            //gra w procesie / game in progress
            var clickedPos = (v?.tag as String).toInt()
        }

    }
}