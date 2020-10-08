package org.pondar.pacmankotlin.GameEntities

import android.content.Context
import android.graphics.BitmapFactory
import org.pondar.pacmankotlin.Interfaces.Characters.Enemy
import org.pondar.pacmankotlin.Interfaces.Characters.FireBall
import org.pondar.pacmankotlin.Interfaces.Characters.SpaceShip
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.Objects.GoldCoin
import org.pondar.pacmankotlin.Interfaces.Objects.Wall
import org.pondar.pacmankotlin.R
import kotlin.collections.ArrayList


class GenerateObjects(var context: Context, var w: Int, var h: Int) {

    var GameObjects = ArrayList<Object2D>()

    var coinsInitialized = false

    var goldTexture = R.drawable.coin

    var Xunits = w / 5
    var Yunits = h / 8

    var Ship = R.drawable.ship

    var Enemy = R.drawable.enemy

    var Player = R.drawable.frame1

    var Maps = Maps()

    var Enemies : ArrayList<Enemy> = ArrayList()



    fun InitPlayer(): FireBall {

        var player = BitmapFactory.decodeResource(context.resources, Player)

        return FireBall(5, Shape2D(Vector2D(), Vector2D(), null), player,  Xunits, Yunits)
    }

    fun InitShip(): SpaceShip {
        var Ship = BitmapFactory.decodeResource(context.resources, Ship)
        return SpaceShip(5, Shape2D(Vector2D(), Vector2D(), null), Ship,  Xunits, Yunits)
    }

    fun InitEntities() {

        coinsInitialized = true

        if (coinsInitialized) {

            Maps.getMap(0).forEachIndexed { rowIndex, row ->

                row.forEachIndexed { elemIndex, elem ->
                    var Y = rowIndex * Yunits
                    var X = elemIndex * Xunits + (Xunits / 3)
                    val dimension = Vector2D(X.toFloat(), Y.toFloat())
                    var size = Vector2D()
                    if (elem == ReMap.coin.value) {

                       GameObjects.add(GoldCoin(context, Shape2D(dimension, size, goldTexture)))
                    }
                    if (elem == ReMap.enemy.value) {

                        var EnemyBitMap = BitmapFactory.decodeResource(context.resources, Enemy)
                        GameObjects.add(Enemy(1, Shape2D(dimension, size, null), EnemyBitMap,  Xunits, Yunits))

                        Enemies.add(Enemy(1, Shape2D(dimension, size, null), EnemyBitMap,  Xunits, Yunits))
                    }
                    if(elem == ReMap.wall.value){
                         size = Vector2D(dimension.x + Xunits /2, dimension.y + Yunits/ 2)
                        GameObjects.add(Wall(context, Shape2D(dimension, size, 25)))
                    }
                }
            }


        }

    }





    fun InitEnvironment(): ArrayList<Object2D> {

        InitEntities()

        return GameObjects
    }

}

enum class ReMap(val value: Int) {
    coin(1), enemy(2), wall(3)
}

