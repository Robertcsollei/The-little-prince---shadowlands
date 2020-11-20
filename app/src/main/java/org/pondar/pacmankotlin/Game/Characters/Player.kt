package org.pondar.pacmankotlin.Game.Characters

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.Adapters.Ranges
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.Interfaces.ICharacter
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Physics.GravityForce
import java.lang.Math.floor
import java.util.*
import kotlin.collections.ArrayList

class Player (override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?,
              override var Xunit: Int, override var Yunit: Int) : Object2D, ICharacter {


    var screenWidth = 550;
    override var speed = 2.0F
    override fun move(initial: Vector2D, gameController: GameController, view: View) {


    }
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(bitmap?.width?.toFloat()!!, bitmap?.height?.toFloat()!!)
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    override var VertexMatrix: ArrayList<ArrayList<Vector2D>>? = null
    var isFalling: Boolean = false

    var movingForward: Boolean = true
    override var BoundingBox: BoundingBox? = BoundingBox(shape.pos.x, shape.pos.y, shape.size.x, shape.size.y)
    var jumpTimer= 0
    var onGround = false

    fun keepMoving(AccePos: Float, width: Int, Height: Int) {

        if(AccePos in -8F..12F) {
            var newPos = width / 2 - AccePos * width / 2 / 12
           // Pos = Vector2D(floor(newPos.toDouble()).toInt().toFloat(), (Height / 8 * 7).toFloat())

        }


    }

    fun jump(): Boolean{
        if(jumpTimer <= 2000){
            jumpTimer++
        }


            if (jumpTimer < 100) {
                Direction.y = -600 / jumpTimer.toFloat() * 0.1F
                onGround = false
            } else {
                Direction.y += 0.1F
                if (onGround) {
                    Direction.y = 0F

                    return false
                }
            }
        return true
    }

    fun Update(){
        Pos.x += Direction.x
        Pos.y += Direction.y

        VertexMatrix = Ranges().CreateVertexMatrix(Pos.x , Pos.x + Size.x, Pos.y, Pos.y + Size.y)
        Log.d("asdasdasd", Arrays.deepToString(arrayOf(VertexMatrix)))
    }


    override fun OnCollison() {

    }

}