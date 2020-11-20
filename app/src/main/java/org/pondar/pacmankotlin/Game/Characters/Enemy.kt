package org.pondar.pacmankotlin.Game.Characters

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.Interfaces.ICharacter
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D

class Enemy(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?,
            override var Xunit: Int, override var Yunit: Int) : ICharacter, Object2D {

    var newSize : Int = Xunit

    var screenWidth = 550;
    override var speed = 2.0F
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(newSize.toFloat(), newSize.toFloat())
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    override var VertexMatrix: ArrayList<ArrayList<Vector2D>>? = null
    var isShooting: Boolean = false
    var Matrix: Matrix = Matrix()
    var movingForward: Boolean = true
    var isDestroyed : Boolean = false
    override var BoundingBox: BoundingBox? = BoundingBox(shape.pos.x, shape.pos.y, shape.size.x, shape.size.y)

    init {
        bitmap = ResizeBitmap.resizeBitmap(bitmap!!, newSize)
        Matrix.postRotate(180F)
        var newBit = Bitmap.createBitmap(bitmap!!, 0,0, bitmap!!.width,
                bitmap!!.height, Matrix, false)
        bitmap = newBit
    }

    override fun move(Initialize: Vector2D, gameController: GameController, view: View) {

        if (movingForward){
            Pos.x += speed

            //isShooting = Pos.x % 35 == 0.0

            if (Pos.x >= screenWidth-newSize)
                movingForward = false
        }
        else {
            Pos.x -= speed
            if(Pos.x <= newSize)
                movingForward = true
        }
    }

    fun keepMoving(w: Int, h: Int){
        if (movingForward){
            Pos.x += speed

            //isShooting = Pos.x % 35 == 0.0

            if (Pos.x >= w-newSize*2)
                movingForward = false
        }
        else {
            Pos.x -= speed
            if(Pos.x <= newSize)
                movingForward = true
        }
    }

    override fun OnCollison() {
        this.isDestroyed = true
    }


}