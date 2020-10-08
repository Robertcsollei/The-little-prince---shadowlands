package org.pondar.pacmankotlin.Interfaces.GameActions

import android.util.Log
import android.util.Size
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.Characters.Enemy
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D

class Collider(var Object: Object2D, var Pos: Vector2D, var size: Vector2D) {


    var Xmid = size.x
    var Ymid = size.y
    var Direction = size
    var BallRadius = size.x / 2

    fun isColliding(): Boolean {

        return Pos.x in Object.Pos.x - Xmid..Object.Pos.x + Xmid && Pos.y in Object.Pos.y - Ymid..Object.Pos.y + Ymid
    }

    fun playerKilled(): Boolean {
        return (Pos.x + 50 in Object.Pos.x - 50..Object.Pos.x + Object.Size.x) && (Pos.y + 50 in Object.Pos.y - 50..Object.Pos.y + Object.Size.y)
        }

    fun CollectableCollision(): Vector2D? {
        if (Object.isCollectable) {

            if (Object.isCollected) {

                return if (!(Pos.x in Object.Pos.x - 10..Object.Pos.x + 50)) {
                    Vector2D(Direction.x * -1, Direction.y)
                } else {

                    Vector2D(Direction.x, Direction.y * -1)
                }

            }

        }
        return null
    }


    fun WallCollision(): Vector2D? {
        if (Object.isStatic && !Object.isCollectable) {

            if ((Pos.x + 12 in Object.Pos.x - 12..Object.Pos.x + Object.Size.x) && (Pos.y + 12 in Object.Pos.y - 12..Object.Pos.y + Object.Size.y)) {

                return if (Pos.x in Object.Pos.x - 10..Object.Size.x + 10) {

                    Vector2D(Direction.x * -1, Direction.y)

                } else {

                    Vector2D(Direction.x, Direction.y * -1)

                }
            }

        }
        return null
    }

    fun EnemyCollision(): Vector2D? {
        // Enemy collision

        if (!Object.isStatic && !Object.isCollectable) {

            if (Object is Enemy && (Object as Enemy).isDestroyed) {

                return if (Pos.x + BallRadius in Object.Pos.x - BallRadius..(Object as Enemy).newSize.toFloat() * 2) {

                    Vector2D(Direction.x * -1, Direction.y)
                } else {

                    Vector2D(Direction.x, Direction.y * -1)
                }
            }
        }
        return null
    }

    fun GeneralColision(): Vector2D {
        var result = Vector2D()

        when {
            CollectableCollision() != null -> {
                result = CollectableCollision()!!
            }
            WallCollision() != null -> {

                result = WallCollision()!!
            }
            EnemyCollision() != null -> {

                result = EnemyCollision()!!
            }
            else -> {
                result = Direction
            }
        }
        return result

    }

    fun OutOfBoundCollision(game: Game, w: Int, h: Int) {
        if (Pos.x > w - size.x || Pos.x < 10) {
            Log.d("FFFFFF", "RRRRR")
            game.newGame()
        }
        if (Pos.y > h - size.y || Pos.y < 10) {

            game.newGame()
        }
    }

}