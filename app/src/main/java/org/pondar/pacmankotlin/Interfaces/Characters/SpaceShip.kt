package org.pondar.pacmankotlin.Interfaces.Characters

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.GameActions.Collider
import org.pondar.pacmankotlin.Interfaces.Objects.Projectile
import java.lang.Math.floor

class SpaceShip (override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?,
                 override var Xunit: Int, override var Yunit: Int) : Object2D, ICharacter {


    var screenWidth = 550;
    override var speed = 2.0F
    override fun move(initial: Vector2D, game: Game, view: View) {


    }

    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(bitmap?.width?.toFloat()!!, bitmap?.height?.toFloat()!!)
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    var movingForward: Boolean = true

    fun keepMoving(AccePos: Float, width: Int, Height: Int) {


        var newPos = width / 2 - AccePos * width/ 2 / 12
        Pos = Vector2D(floor(newPos.toDouble()).toInt().toFloat(), (Height / 8 * 7).toFloat())


    }

    override fun OnCollison() {

    }

}