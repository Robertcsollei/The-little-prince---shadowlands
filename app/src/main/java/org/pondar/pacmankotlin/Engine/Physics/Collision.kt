package org.pondar.pacmankotlin.Engine.Physics

import android.util.Log
import org.pondar.pacmankotlin.Engine.Adapters.Ranges
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Game.Characters.Enemy
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D

class Collision(var Object: Object2D, var Pos: Vector2D, var size: Vector2D) {


    var xMid = size.x
    var yMid = size.y
    var directions = size
    var ballRadius = size.x / 2

    infix fun <T> Array<T>.isIn(array: Array<Array<T>>): Boolean {
        return array.any { it.contentEquals(this) }
    }


    fun DirectionalCollision() {

    }

    fun isColliding(): Boolean {

        return Pos.x in Object.Pos.x - xMid..Object.Pos.x + xMid && Pos.y in Object.Pos.y - yMid..Object.Pos.y + yMid
    }

    fun playerKilled(): Boolean {
        return (Pos.x + 50 in Object.Pos.x - 50..Object.Pos.x + Object.Size.x) && (Pos.y + 50 in Object.Pos.y - 50..Object.Pos.y + Object.Size.y)
        }

    fun CollectableCollision(): Vector2D? {
        if (Object.isCollectable) {

            if (Object.isCollected) {

                return if (!(Pos.x in Object.Pos.x - 10..Object.Pos.x + 50)) {
                    Vector2D(directions.x * -1, directions.y)
                } else {

                    Vector2D(directions.x, directions.y * -1)
                }

            }

        }
        return null
    }





    fun WallCollision(): Vector2D? {

        if (Object.isStatic && !Object.isCollectable) {

            val ySize = Object.BoundingBox?.Size!!.y
            val xSize = Object.BoundingBox?.Size!!.x

            //Range on the X axis on which I am checking the collision of two bounding boxes
            val xMin = Object.BoundingBox?.Pos!!.x
            val xMax = Object.BoundingBox?.Pos!!.x + xSize
            var xRange = xMin.. xMax


            //Range on the Y axis on which I am checking the collision of two bounding boxes
            val yMin = Object.BoundingBox?.Pos!!.y
            val yMax = Object.BoundingBox?.Pos!!.y + ySize
            var yRange =  yMin.. yMax


            if(Object.VertexMatrix == null){

               Object.VertexMatrix = Ranges().CreateVertexMatrix(xMin, xMax, yMin, yMax)
                Log.d("asdasdasda", Object.VertexMatrix!![0][0].toString())

            }

            //Collision on both axis
            if (Ranges().RangeContainsEither(Pos, size, xRange, yRange)){

                //Collision on the Y axis, falling down
                if (Ranges().RangeContains(Pos.y, size.y, yRange))
                {
                    Log.d("HHHHHHHH","as")
                    return if(Pos.y < yMin + (ySize / 2)){
                        Vector2D(0F,1F)
                    }else{
                        Vector2D(0F, -1F)
                    }
                }

                //Collision on the X axis, hitting a wall
                if(Ranges().RangeContains(Pos.x, size.x, xRange)) {
                    Log.d("HHHHHHHH", "X")
                    return if(Pos.x < xMin + (xSize / 2)){
                        Vector2D(-1F, 0F)
                    }else{
                        Vector2D(1F, 0F)
                    }
                }else{
                    return Vector2D()
                }

            }



            else{

                return null
            }

        }
        return null
    }

    fun EnemyCollision(): Vector2D? {
        // Enemy collision

        if (!Object.isStatic && !Object.isCollectable) {

            if (Object is Enemy && (Object as Enemy).isDestroyed) {

                return if (Pos.x + ballRadius in Object.Pos.x - ballRadius..(Object as Enemy).newSize.toFloat() * 2) {

                    Vector2D(directions.x * -1, directions.y)
                } else {

                    Vector2D(directions.x, directions.y * -1)
                }
            }
        }
        return null
    }

    fun GeneralColision(): Vector2D {
        var result = Vector2D()

        result = when {
            CollectableCollision() != null -> {
                CollectableCollision()!!
            }

            EnemyCollision() != null -> {

                EnemyCollision()!!
            }
            else -> {
                directions
            }
        }
        return result

    }

    fun OutOfBoundCollision(gameController: GameController, w: Int, h: Int) {
        if (Pos.x > w - size.x || Pos.x < 10) {
            Log.d("FFFFFF", "RRRRR")
           // Vector2D(Direction.x * -1, Direction.y)
        }
        if (Pos.y > h - size.y || Pos.y < 10) {

            //Vector2D(Direction.x, Direction.y * -1)
        }
    }

}