package org.pondar.pacmankotlin.Interfaces.Objects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.GameActions.Collider


class GoldCoin(context: Context, newShape: Shape2D) : Object2D{

    override var shape: Shape2D = newShape

    override var bitmap: Bitmap? = BitmapFactory.decodeResource(context.resources, shape.color!!)
   override var Pos = shape.pos
    //Figure this out
   override var Size = Vector2D(bitmap?.width?.toFloat()!!, bitmap?.height?.toFloat()!!)

    override var isStatic: Boolean = true
    override var isCollectable: Boolean = true
    override var isCollected = false
    var ResizeBit = BitMapConverter()
    var speed: Float = 2F

    init{

        bitmap = ResizeBit.resizeBitmap(bitmap!!, 60)
    }

    override fun OnCollison() {
        isCollected = true

    }

    fun keepMoving(game: Game) {



        Pos.y += speed
    }




}