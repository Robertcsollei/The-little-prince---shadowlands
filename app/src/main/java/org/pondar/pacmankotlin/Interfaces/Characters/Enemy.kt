package org.pondar.pacmankotlin.Interfaces.Characters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.GameActions.Collision
import org.pondar.pacmankotlin.Interfaces.Objects.Projectile
import java.util.*

class Enemy(override val life: Int, override val picture: Bitmap,
            override var posX: Double, override var posY: Double,
            var screenWidth: Int, var screenHeight: Int) : ICharacter{

    override var InitialX = 0
    override var InitialY = 0
    override var EndX = 0
    override var EndY = 0
    override var speed = 3
    var Projectile: Projectile = Projectile()
    var isShooting: Boolean = false

    var BitmapImage : Bitmap = resizeBitmap(picture,150)
    var movingForward: Boolean = true

    override fun move(initialX: Int, initialY: Int, game: Game, view: View) {
        Log.d("Canvas" , "" + screenWidth)
        if (movingForward){
            posX += speed

            isShooting = posX % 35 == 0.0

            if (posX >= screenWidth-250)
                movingForward = false
        }
        else {
            posX -= speed
            if(posX <= 5)
                movingForward = true
        }
    }

    override fun collision() {
        TODO("Not yet implemented")
    }

    fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap {
        try {
            if (source.height >= source.width) {
                if (source.height <= maxLength) { // if image height already smaller than the required height
                    return source
                }

                val aspectRatio = source.width.toDouble() / source.height.toDouble()
                val targetWidth = (maxLength * aspectRatio).toInt()
                val result = Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
                return result
            } else {
                if (source.width <= maxLength) { // if image width already smaller than the required width
                    return source
                }

                val aspectRatio = source.height.toDouble() / source.width.toDouble()
                val targetHeight = (maxLength * aspectRatio).toInt()

                val result = Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
                return result
            }
        } catch (e: Exception) {
            return source
        }
    }
}