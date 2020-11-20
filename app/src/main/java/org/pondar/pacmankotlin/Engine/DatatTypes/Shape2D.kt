package org.pondar.pacmankotlin.Engine.DatatTypes

import android.graphics.drawable.ShapeDrawable


class Shape2D(dimensions: Vector2D, size: Vector2D, var  color: Int?) {

    lateinit var shapeDrawable: ShapeDrawable

    var pos = dimensions
    var size = size
    var left = dimensions.x
    var top = dimensions.y
    var right = size.x
    var bottom = size.y



}