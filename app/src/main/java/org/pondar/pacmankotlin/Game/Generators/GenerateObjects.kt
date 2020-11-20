package org.pondar.pacmankotlin.Game.Generators

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import org.pondar.pacmankotlin.Engine.Adapters.Ranges
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Game.Characters.Enemy
import org.pondar.pacmankotlin.Game.Characters.Player
import org.pondar.pacmankotlin.Game.GameObjects.FireBall
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Game.GameObjects.GoldCoin
import org.pondar.pacmankotlin.Game.GameObjects.Wall
import org.pondar.pacmankotlin.R
import kotlin.collections.ArrayList


class GenerateObjects(var context: Context, var w: Int, var h: Int) {

    var GameObjects = ArrayList<Object2D>()

    var coinsInitialized = false

    var goldTexture = R.drawable.meteor

    var Xunits = w / 9
    var Yunits = h / 16

    var Player = R.drawable.adventurer_test

    var Enemy = R.drawable.enemy

    var FireBall = R.drawable.frame1

    var Maps = org.pondar.pacmankotlin.Game.Maps.Maps()

    var Enemies : ArrayList<Enemy> = ArrayList()



    fun InitFireBall(): FireBall {

        var player = BitmapFactory.decodeResource(context.resources, FireBall)

        return FireBall(5, Shape2D(Vector2D(), Vector2D(), null), player,  Xunits, Yunits)
    }

    fun InitPlayer(): Player {
        for( x in 0..19){
            Log.d("Collider", "${x * Xunits}")
        }
        val startingPosition = Vector2D(Xunits * 5F + 1 ,10F)

        val initialSize = Vector2D() // Will ve overwritten! The size is bitmap size dependent

        var PlayerBitmap = BitmapFactory.decodeResource(context.resources, Player)
        Log.d("Xunits", (Xunits).toString())
        return Player(5, Shape2D(startingPosition, initialSize, null), PlayerBitmap,  Xunits, Yunits)

    }

    fun InitEntities(mapNo: Int) {

        GameObjects.clear()
        coinsInitialized = true

        if (coinsInitialized) {

            Maps.getMap(mapNo).forEachIndexed { rowIndex, row ->

                row.forEachIndexed { elemIndex, elem ->
                    var Y = rowIndex * Yunits
                    var X = elemIndex * Xunits

                    val dimension = Vector2D(X.toFloat(), Y.toFloat())
                    var size = Vector2D()
                    if (elem == ReMap.coin.value) {

                       GameObjects.add(GoldCoin(context, Shape2D(dimension, size, goldTexture), Xunits, Yunits))
                    }
                    if (elem == ReMap.enemy.value) {

                        var EnemyBitMap = BitmapFactory.decodeResource(context.resources, Enemy)
                        GameObjects.add(Enemy(1, Shape2D(dimension, size, null), EnemyBitMap,  Xunits, Yunits))

                        Enemies.add(Enemy(1, Shape2D(dimension, size, null), EnemyBitMap,  Xunits, Yunits))
                    }
                    if(elem == ReMap.wall.value){
                         size = Vector2D(dimension.x + Xunits , dimension.y + Yunits)
                        Log.d("HEEEEX", X.toString())
                        Log.d("HEEEEX+", (X+Xunits).toString())
                        Log.d("HEEEEY", Y.toString())
                        GameObjects.add(Wall(context, Shape2D(dimension, size, 25), BoundingBox(X.toFloat(), Y.toFloat(), Xunits.toFloat(), Yunits.toFloat())))
                    }
                }
            }


        }

    }





    fun InitEnvironment(mapNo: Int): ArrayList<Object2D> {
        Enemies.clear()
        InitEntities(mapNo)

        return GameObjects
    }

}

enum class ReMap(val value: Int) {
    coin(1), enemy(2), wall(3)
}

