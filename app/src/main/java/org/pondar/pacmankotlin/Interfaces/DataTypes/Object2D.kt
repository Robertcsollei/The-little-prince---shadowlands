package org.pondar.pacmankotlin.Interfaces.DataTypes

import android.graphics.Bitmap

interface Object2D {

    var bitmap: Bitmap?
    var shape: Shape2D
    var Pos: Vector2D
    var Size: Vector2D
    var isStatic: Boolean
    var isCollectable: Boolean
    var isCollected: Boolean
    fun OnCollison()
}