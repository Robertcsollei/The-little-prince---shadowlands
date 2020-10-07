package org.pondar.pacmankotlin.Interfaces.Objects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D


class GoldCoin(context: Context, newShape: Shape2D) : Object2D{

    override var shape: Shape2D = newShape

    override var bitmap: Bitmap? = BitmapFactory.decodeResource(context.resources, shape.color!!)
   override var Pos = shape.pos
    //Figure this out
   override var Size = Vector2D(bitmap?.width?.toFloat()!!, bitmap?.height?.toFloat()!!)

    override var isStatic: Boolean = true
    override var isCollectable: Boolean = true
    var goldBitmap: Bitmap
    override var isCollected = false

    init{
       goldBitmap = BitmapFactory.decodeResource(context.resources, shape.color!!)
    }

    override fun OnCollison() {
        isCollected = true

    }




}