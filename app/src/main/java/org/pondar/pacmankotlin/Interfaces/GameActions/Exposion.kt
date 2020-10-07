package org.pondar.pacmankotlin.Interfaces.GameActions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.R

class Exposion (var context: Context) {

    var bitmap: Bitmap
    var Pos = Vector2D()
    var end = 1600
    var ExposionSprites = arrayListOf<Int>(R.drawable.exploasion1, R.drawable.exploasion2, R.drawable.exploasion3,
            R.drawable.exploasion4, R.drawable.exploasion5, R.drawable.exploasion6, R.drawable.exploasion7)

    var switchImage = 0

    init {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.exploasion1)
    }

    fun Explode(coundDown: Int, DestoryedPos: Vector2D, game: Game, context: Context) {
        Pos = DestoryedPos

        if(switchImage < 7) {
            bitmap = BitmapFactory.decodeResource(context.resources, ExposionSprites.elementAt(switchImage))
        }else{
           game.playExplosion = false
            game.Explosion = Exposion(context)

        }
        switchImage++

    }

}