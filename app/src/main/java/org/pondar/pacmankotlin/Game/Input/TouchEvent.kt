package org.pondar.pacmankotlin.Game.Input

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import org.pondar.pacmankotlin.Engine.Collections.Orientations
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
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

                gameController!!.gravityOn = true

            }
            MotionEvent.ACTION_MOVE -> {


            }
            MotionEvent.ACTION_UP -> {
                tEnd = System.currentTimeMillis()



            }
        }
        val tDelta: Long = tEnd - tStart

        val elapsedSeconds = tDelta / 1000.0

        if(elapsedSeconds > 0.05){

            Toast.makeText(main, "click", Toast.LENGTH_SHORT).show()
            gameController?.Player!!.jump = 5F
            gameController!!.Player.jumpTimer = 0


        }

        gameView?.invalidate()


        return true
    }
}