package org.pondar.pacmankotlin.Interfaces.Objects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Interfaces.Characters.ICharacter
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.R

class Projectile(override var Initial: Vector2D,  override var Xunit: Int,  override var Yunit: Int, var context: Context ): ICharacter {

    override var Direction: Vector2D = Vector2D(0F,1F)
    override var speed: Float = 4F
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    override val life: Int = 1
    lateinit var bitmap: Bitmap
    var isShooting: Boolean = false
    var Pos = Vector2D(Initial.x, Initial.y)

    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.projectile)
        var newProjectile = ResizeBitmap.resizeBitmap(bitmap, Xunit/2)
        bitmap = newProjectile
    }

    override fun move(initial: Vector2D, game: Game, view: View) {

        Pos.y = Direction.y

    }

    fun keepMoving() {
        Pos.y += speed
    }
}