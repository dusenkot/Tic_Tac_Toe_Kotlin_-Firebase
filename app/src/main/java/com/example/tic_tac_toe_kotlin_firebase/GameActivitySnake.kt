package com.example.tic_tac_toe_kotlin_firebase

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GameActivitySnake : AppCompatActivity() {

    companion object {
        @kotlin.jvm.JvmField
        var dialogScore: Dialog? = null
    }


    private var gameView: SnakeGameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        ConstantsSnake.SCREEN_WIDTH = dm.widthPixels
        ConstantsSnake.SCREEN_HEIGHT = dm.heightPixels

        setContentView(R.layout.activity_game_snake)

        gameView?.requestFocus()


        dialogScore()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return gameView?.onKeyDown(keyCode, event) ?: super.onKeyDown(keyCode, event)
    }

    public fun dialogScore() {

        dialogScore = Dialog(this).apply {
            setContentView(R.layout.dialog_start)
            setCanceledOnTouchOutside(false)
            findViewById<RelativeLayout>(R.id.rl_start).setOnClickListener {
                gameView?.reset()
                dismiss()
            }
        }
        dialogScore?.show()
    }


}