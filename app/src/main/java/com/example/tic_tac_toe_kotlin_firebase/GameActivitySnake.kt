package com.example.tic_tac_toe_kotlin_firebase

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.AbsSavedState
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toe_kotlin_firebase.databinding.ActivityGameBinding
import android.view.Window;
import android.view.WindowManager;


class GameActivitySnake : AppCompatActivity() {
    private var gameView: SnakeGameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Retrieve display metrics to get screen dimensions
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        ConstantsSnake.SCREEN_HEIGHT = dm.heightPixels
        ConstantsSnake.SCREEN_WIDTH = dm.widthPixels

        setContentView(R.layout.activity_game_snake)
        gameView = findViewById(R.id.gv)

        // Ensure the GameView is focusable and set up to receive key events
        gameView?.isFocusable = true
        gameView?.isFocusableInTouchMode = true
        gameView?.requestFocus()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Pass the key event to the GameView for handling
        return gameView?.onKeyDown(keyCode, event) ?: false
    }
}