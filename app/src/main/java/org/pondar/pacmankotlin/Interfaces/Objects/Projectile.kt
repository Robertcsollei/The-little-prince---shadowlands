package org.pondar.pacmankotlin.Interfaces.Objects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Interfaces.Characters.FireBall
import org.pondar.pacmankotlin.Interfaces.Characters.ICharacter
import org.pondar.pacmankotlin.Interfaces.Characters.SpaceShip
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.GameActions.Collider
import org.pondar.pacmankotlin.R

class Projectile(override var Initial: Vector2D,  override var Xunit: Int,
                 override var Yunit: Int, var context: Context,var game: Game? ): ICharacter {

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

    override fun move(initial: Vector2D, game: Game, view: View) {

        Pos.y = Direction.y

    }

    fun keepMoving() {

        var collider: Collider = Collider(game?.SpaceShip!!, Pos, Direction)
        if(collider.playerKilled()){
           game?.newGame()

        }

        Pos.y += speed
    }
}