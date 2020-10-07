package org.pondar.pacmankotlin.Interfaces.Characters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.Objects.Projectile

class Enemy(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?) : ICharacter, Object2D{


    var screenWidth = 550;
    override var speed = 2.0F
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(bitmap?.width?.toFloat()!!, bitmap?.height?.toFloat()!!)
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    var Projectile: Projectile = Projectile()
    var isShooting: Boolean = false

    var movingForward: Boolean = true





    override fun move(Initialize: Vector2D, game: Game, view: View) {
        Log.d("Canvas" , "" + screenWidth)
        if (movingForward){
            Pos.x += speed

            //isShooting = Pos.x % 35 == 0.0

            if (Pos.x >= screenWidth-250)
                movingForward = false
        }
        else {
            Pos.x -= speed
            if(Pos.x <= 5)
                movingForward = true
        }
    }

    override fun OnCollison() {
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