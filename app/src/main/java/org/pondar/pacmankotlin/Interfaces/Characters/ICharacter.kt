package org.pondar.pacmankotlin.Interfaces.Characters

import android.graphics.Bitmap
import android.view.MotionEvent
import android.view.View
import org.pondar.pacmankotlin.Game

interface ICharacter {

    val life: Int
    val picture: Bitmap
    var posX: Double
    var posY: Double
    var InitialX : Int
    var InitialY : Int
    var EndX : Int
    var EndY : Int
    var speed : Int

    fun move(initialX: Int, initialY: Int, game: Game, view: View)

    fun collision()


}