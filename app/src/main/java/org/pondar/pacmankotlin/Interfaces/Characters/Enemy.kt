package org.pondar.pacmankotlin.Interfaces.Characters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D

class Enemy(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?,
            override var Xunit: Int, override var Yunit: Int) : ICharacter, Object2D{

    var newSize : Int = Xunit

    var screenWidth = 550;
    override var speed = 2.0F
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(newSize.toFloat(), newSize.toFloat())
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    var isShooting: Boolean = false
    var Matrix: Matrix = Matrix()
    var movingForward: Boolean = true
    var isDestroyed : Boolean = false

    init {
        bitmap = ResizeBitmap.resizeBitmap(bitmap!!, newSize)
        Matrix.postRotate(180F)
        var newBit = Bitmap.createBitmap(bitmap!!, 0,0, bitmap!!.width,
                bitmap!!.height, Matrix, false)
        bitmap = newBit
    }

    override fun move(Initialize: Vector2D, game: Game, view: View) {
        Log.d("Canvas" , "" + screenWidth)
        if (movingForward){
            Pos.x += speed

            //isShooting = Pos.x % 35 == 0.0

            if (Pos.x >= screenWidth-newSize)
                movingForward = false
        }
        else {
            Pos.x -= speed
            if(Pos.x <= newSize)
                movingForward = true
        }
    }

    fun keepMoving(w: Int, h: Int){
        if (movingForward){
            Pos.x += speed

            //isShooting = Pos.x % 35 == 0.0

            if (Pos.x >= w-newSize*2)
                movingForward = false
        }
        else {
            Pos.x -= speed
            if(Pos.x <= newSize)
                movingForward = true
        }
    }

    override fun OnCollison() {
        this.isDestroyed = true
    }


}