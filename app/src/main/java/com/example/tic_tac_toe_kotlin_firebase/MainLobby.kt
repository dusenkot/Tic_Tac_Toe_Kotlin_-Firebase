package com.example.tic_tac_toe_kotlin_firebase;

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
class MainLobby: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_lobby)

        val ticTacToeButton: Button = findViewById(R.id.gotoTicTacToe)
        ticTacToeButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        val snakeGameButton: Button = findViewById(R.id.snakeGame)
        snakeGameButton.setOnClickListener {
            startActivity(Intent(this,GameActivitySnake::class.java))
        }
    }
}
