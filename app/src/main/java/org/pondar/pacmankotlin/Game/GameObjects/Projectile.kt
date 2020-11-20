package org.pondar.pacmankotlin.Game.GameObjects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.Interfaces.ICharacter
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Physics.Collision
import org.pondar.pacmankotlin.R

class Projectile(override var Initial: Vector2D, override var Xunit: Int,
                 override var Yunit: Int, var context: Context, var gameController: GameController? ): ICharacter {

    override var Direction: Vector2D = Vector2D(0F,1F)
    override var speed: Float = 4F
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    var size = Xunit / 2
    override val life: Int = 1
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.projectile)
    var isShooting: Boolean = false
    var Pos = Vector2D(Initial.x, Initial.y)


    init {
        var newProjectile = ResizeBitmap.resizeBitmap(bitmap, size)
        bitmap = newProjectile
    }

    override fun move(initial: Vector2D, gameController: GameController, view: View) {

        Pos.y = Direction.y

    }

    fun keepMoving() {

        var collision: Collision = Collision(gameController?.Player!!, Pos, Direction)
        if(collision.playerKilled()){
           gameController?.newGame()

        }

        Pos.y += speed
    }
}