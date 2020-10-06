package org.pondar.pacmankotlin.Interfaces.Characters

import android.graphics.Bitmap
import android.util.Log
import android.view.MotionEvent
import android.view.View
import org.pondar.pacmankotlin.Game
import kotlin.math.pow
import kotlin.math.sqrt

class PacMan(override val life: Int, override val picture: Bitmap,
             override var posX: Double, override var posY: Double) : ICharacter {

    override var InitialX = 0
    override var InitialY = 0
    override var EndX = 0
    override var EndY = 0
    override var speed = 2
    var isMoving = false
    var acceleration:Double = 2.0
    var accSpeed = 5.0

    var descSpeed = 0.5

    override fun move(x: Int, y: Int, game: Game, view: View){
        EndX = x - InitialX
        EndY = y - InitialY
        Log.i("TAG", "$EndX $EndY")

        if(isMoving) {
            game.setPacPosition(10)
        }
        view.invalidate()
    }

    fun keepMoving(){
        if(!(EndX in 50 downTo -50 && EndY in 50 downTo -50)) {

            var len = sqrt(EndX.toDouble().pow(2.0) + EndY.toDouble().pow(2.0))

            posX += EndX / len * speed
            posY += EndY / len * speed
        }
    }

    fun accelerate(){
        acceleration *= accSpeed
    }

    fun normalize() {
        if(acceleration > 0.0){
            acceleration -= descSpeed
        }
        if(acceleration < 0.0){
            acceleration += descSpeed
        }
    }

    override fun collision(){

    }

}