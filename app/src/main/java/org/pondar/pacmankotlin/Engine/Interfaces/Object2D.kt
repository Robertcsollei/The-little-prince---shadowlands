package org.pondar.pacmankotlin.Engine.Interfaces

import android.graphics.Bitmap
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D

interface Object2D {

    var bitmap: Bitmap?
    var shape: Shape2D
    var Pos: Vector2D
    var Size: Vector2D
    var isStatic: Boolean
    var isCollectable: Boolean
    var isCollected: Boolean
    var BoundingBox: BoundingBox?
    var VertexMatrix: ArrayList<ArrayList<Vector2D>>?
    var isGround: Boolean
    var dead: Boolean
    fun OnCollison()
}