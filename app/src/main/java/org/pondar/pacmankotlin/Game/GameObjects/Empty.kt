package org.pondar.pacmankotlin.Game.GameObjects

import android.content.Context
import android.graphics.Bitmap
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D

class Empty(context: Context, newShape: Shape2D, override var BoundingBox: BoundingBox?, override var isGround: Boolean, override var bitmap: Bitmap? = null) : Object2D {



    override var shape: Shape2D = newShape

    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = shape.size
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var isStatic: Boolean = true
    override var dead = false
    override var VertexMatrix: ArrayList<ArrayList<Vector2D>>? = null
    override fun OnCollison() {

    }

}