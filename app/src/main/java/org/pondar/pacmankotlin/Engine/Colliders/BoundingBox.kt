package org.pondar.pacmankotlin.Engine.Colliders

import android.content.Context
import android.graphics.Bitmap
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D

class BoundingBox(Posx: Float, Posy: Float, Sizex: Float, Sizey: Float)  {

     var Pos: Vector2D = Vector2D(Posx, Posy)
     var Size: Vector2D = Vector2D(Sizex, Sizey)
     var shape: Shape2D = Shape2D(Pos, Size, null)

     constructor(empty: Boolean) : this(0F, 0F, 0F , 0F )
     constructor() : this(0F, 0F, 0F , 0F ){

}
}