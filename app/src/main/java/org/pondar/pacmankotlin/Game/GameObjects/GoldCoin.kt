package org.pondar.pacmankotlin.Game.GameObjects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D


class GoldCoin(context: Context, newShape: Shape2D, var XUnit: Int, var YUnit: Int) : Object2D {

    override var shape: Shape2D = newShape

    override var bitmap: Bitmap? = BitmapFactory.decodeResource(context.resources, shape.color!!)
   override var Pos = shape.pos
    override var dead = false
    //Figure this out
   override var Size = Vector2D(bitmap?.width?.toFloat()!!, bitmap?.height?.toFloat()!!)
    override var VertexMatrix: ArrayList<ArrayList<Vector2D>>? = null
    override var isStatic: Boolean = true
    override var isCollectable: Boolean = true
    override var isCollected = false
    override var isGround: Boolean = false
    var ResizeBit = BitMapConverter()
    var speed: Float = 2F
    override var BoundingBox: BoundingBox? = BoundingBox(shape.pos.x, shape.pos.y, shape.size.x, shape.size.y)
    init{

        bitmap = ResizeBit.resizeBitmap(bitmap!!, XUnit)
    }

    override fun OnCollison() {
        isCollected = true

    }

    fun keepMoving(gameController: GameController) {



        Pos.y += speed
    }




}