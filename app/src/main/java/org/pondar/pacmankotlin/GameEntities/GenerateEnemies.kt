package org.pondar.pacmankotlin.GameEntities

import android.content.Context
import android.graphics.BitmapFactory
import org.pondar.pacmankotlin.Interfaces.Characters.Enemy
import org.pondar.pacmankotlin.R
import java.util.*
import kotlin.collections.ArrayList

class GenerateEnemies {

    var Enemies: ArrayList<Enemy> = ArrayList()

    fun InitializeEnemies(numberOfEnemies: Int, context: Context, CanvasWidth: Int?, CanvasHeight: Int?) : ArrayList<Enemy>{
        val random = Random()
        for (x in 0..numberOfEnemies){
            Enemies.add(Enemy(1, BitmapFactory.decodeResource(context.resources, R.drawable.enemy),
                    random.nextInt(1600).toDouble(), random.nextInt(1600).toDouble(),
                    CanvasWidth!!, CanvasHeight!!))
        }

        return Enemies
    }
}