package org.pondar.pacmankotlin.Interfaces.DataTypes

import android.util.Log
import java.lang.Math.pow
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Vector2D(VecX: Float, VecY:Float){

    var x = VecX
    var y = VecY


    fun Normalize(offset: Float?): Vector2D{
        var Size = sqrt(pow(this.x.toDouble(), 2.0) + pow(this.y.toDouble(), 2.0)).toFloat()
        val Normal :Vector2D = Vector2D()
        Normal.x = this.x / Size * offset!!
        Normal.y = this.y / Size * offset
        Log.d("MATRIX", Normal.x.toString() + " NORMAL " + Normal.y.toString())
        return Normal
    }

    fun Length(): Float {
        var Size = sqrt(pow(this.x.toDouble(), 2.0) + pow(this.y.toDouble(), 2.0)).toFloat()
        return Size
    }

    fun Substract(Elem: Vector2D?): Vector2D {
        val SubResult = Vector2D()
        SubResult.x = this.x - Elem!!.x
        SubResult.y = this.y - Elem.y
        return SubResult
    }

    fun Add(Elem: Vector2D?): Vector2D {

        this.x +=  Elem!!.x
        this.y +=  Elem.y
        return this
    }

    fun Rotate(angle: Double): Vector2D {

        var newX = ((cos(angle) * this.x) - (sin(angle) * this.y)).toFloat()
        var newY = (sin(angle) * this.x + cos(angle) * this.y).toFloat()
        var RotResult = Vector2D(newX, newY)
        return RotResult
    }

    constructor() : this(0F,0F)



}