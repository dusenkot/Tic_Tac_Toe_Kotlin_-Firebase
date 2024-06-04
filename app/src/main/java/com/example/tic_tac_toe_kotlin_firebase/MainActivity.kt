package com.example.tic_tac_toe_kotlin_firebase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tic_tac_toe_kotlin_firebase.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playOfflineBtn.setOnClickListener {
            createOfflineGame()

            fun onPlayOfflineClicked() {
                val animation = AnimationUtils.loadAnimation(this, R.anim.button_scale)
                binding.playOfflineBtn.startAnimation(animation)
            }
            onPlayOfflineClicked()

        }

        binding.createOnlineGameBtn.setOnClickListener {
            createOnlineGame()
            fun oncreateOnlineGame() {
                val animation = AnimationUtils.loadAnimation(this, R.anim.button_scale)
                binding.createOnlineGameBtn.startAnimation(animation)
            }
            oncreateOnlineGame()
        }

        binding.joinOnlinePlayBtn.setOnClickListener {
            joinOnlineGame()
            fun onjoinOnlinePlayBtn() {
                val animation = AnimationUtils.loadAnimation(this, R.anim.button_scale)
                binding.joinOnlinePlayBtn.startAnimation(animation)
            }
            onjoinOnlinePlayBtn()
        }
        binding.SnakeGame.setOnClickListener{
            createSnakeGame()
        }


    }


    fun createSnakeGame(){
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED
            )
        )
        startGameSnake()
    }

    fun createOfflineGame(){
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.JOINED
            )
        )
        startGame()
    }

    fun createOnlineGame(){
        GameData.myID = "X"
        GameData.saveGameModel(
            GameModel(
                gameStatus = GameStatus.CREATED,
                gameId = Random.nextInt(1000..9999).toString()
            )
        )
        startGame()
    }

    fun joinOnlineGame(){
        var gameId = binding.gameIdInput.text.toString()
        if(gameId.isEmpty()){
            binding.gameIdInput.setError("Please enter game ID")
            return
        }
        GameData.myID = "O"
        Firebase.firestore.collection("games")
            .document(gameId)
            .get()
            .addOnSuccessListener {
                val model = it?.toObject(GameModel::class.java)
                if(model==null){
                    binding.gameIdInput.setError("Please enter valid game ID")
                }else{
                    model.gameStatus = GameStatus.JOINED
                    GameData.saveGameModel(model)
                    startGame()
                }
            }

    }

    fun startGame(){
        startActivity(Intent(this,GameActivity::class.java))
    }

    fun startGameSnake(){
        startActivity(Intent(this,GameActivitySnake::class.java))
    }


}
