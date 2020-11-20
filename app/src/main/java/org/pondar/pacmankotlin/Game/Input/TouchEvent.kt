package org.pondar.pacmankotlin.Game.Input

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.GameView
import org.pondar.pacmankotlin.MainActivity


class TouchEvent(var gameController: GameController?, var gameView: GameView?, var main: MainActivity)  :   View.OnTouchListener {
    var tStart = 0L
    var tEnd = 0L
     @SuppressLint("ClickableViewAccessibility")
     fun setListener(){
        gameView?.setOnTouchListener(this)
      }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {


        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                tStart = System.currentTimeMillis()

            }
            MotionEvent.ACTION_MOVE -> {

            }
            MotionEvent.ACTION_UP -> {
                tEnd = System.currentTimeMillis()


            }
        }
        val tDelta: Long = tEnd - tStart

        val elapsedSeconds = tDelta / 1000.0

        if(elapsedSeconds > 0.1){
            Log.d("TIMEE", elapsedSeconds.toString())
            Toast.makeText(main, "click", Toast.LENGTH_SHORT).show()
            gameController?.jump = true
            gameController!!.Player.jumpTimer = 0

        }

        gameView?.invalidate()


        return true
    }
}