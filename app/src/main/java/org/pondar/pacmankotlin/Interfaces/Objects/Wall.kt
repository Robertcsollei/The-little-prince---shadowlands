package org.pondar.pacmankotlin.Interfaces.Objects

import android.content.Context
import android.graphics.Bitmap
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D

class Wall(context: Context, newShape: Shape2D) : Object2D {


    override var shape: Shape2D = newShape
    override var bitmap: Bitmap? = null
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = shape.size
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var isStatic: Boolean = true
    override fun OnCollison() {

    }



}