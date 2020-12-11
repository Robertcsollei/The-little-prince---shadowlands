package org.pondar.pacmankotlin.Engine.Physics

import android.util.Log
import org.pondar.pacmankotlin.Engine.Adapters.Ranges
import org.pondar.pacmankotlin.Engine.Collections.Orientations
import org.pondar.pacmankotlin.Engine.DatatTypes.CollisionObject
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Game.Characters.Enemy
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Game.GameObjects.Empty

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


    fun WallCollision(): CollisionObject? {


        val ySize = Object.BoundingBox?.Size!!.y
        val xSize = Object.BoundingBox?.Size!!.x

        //Range on the X axis on which I am checking the collision of two bounding boxes
        val xMin = Object.BoundingBox?.Pos!!.x
        val xMax = Object.BoundingBox?.Pos!!.x + xSize
        var xRange = xMin..xMax


        /*
        * check point in triangle method
        * pass in all 4 points of the obj to Check ==> Player
        * return the name of the triangle where the collision happened
        *
        * */

        //Range on the Y axis on which I am checking the collision of two bounding boxes
        val yMin = Object.BoundingBox?.Pos!!.y
        val yMax = Object.BoundingBox?.Pos!!.y + ySize
        val yRange = yMin..yMax

        val bigXRange = Pos.x - xSize..Pos.x + size.x + xSize
        val bigYRange = Pos.y - ySize..Pos.y + size.y + ySize

        var collisionObject = CollisionObject()

        if (Ranges().RangeContains(xMin,xSize, bigXRange)) {
            if(Object is Enemy){
              collisionObject.isEnemey = true
            }
            collisionObject.ReachableBoxDetected = true
            collisionObject.target = Object

            //If collision happens
            if (Ranges().RangeContainsBoth(Pos, size, xRange, yRange)) {

            if(Object is Empty){
                collisionObject.isEmpty = true
            }


                val corners = arrayOf(Vector2D(Pos.x, Pos.y), Vector2D(Pos.x + size.x, Pos.y), Vector2D(Pos.x, Pos.y + size.y), Vector2D(Pos.x + size.x, Pos.y + size.y))



//                var collisionSides = ArrayList<String>()
//                var groundCollision = ArrayList<String>()

                corners.forEachIndexed { index, it ->

                    val colOfPoint = Ranges().collisionDirection(it, xMin, xMax, yMin, yMax)


                    if (!colOfPoint.Equals(Vector2D())) {

                        if (!Object.isGround) {

                            collisionObject.collisionDirections.add(Orientations.Down.Direction)
                        } else {
                            if (Object.isGround) {
                                collisionObject.ground = true
                            }
                        }
                    }
                }




                return collisionObject

//
//                if(groundCollision.contains("top")){
//                    return Orientations.Up.Direction
//                }
//
//                else if (collisionSides.count() != 0){
//
//
//
//                    when {
//                        collisionSides.contains("right") -> {
//                            return Orientations.Left.Direction
//                        }
//                        collisionSides.contains("bottom") -> {
//                            return Orientations.Down.Direction
//                        }
//                        collisionSides.contains("left") -> {
//                            return Orientations.Right.Direction
//                        }
//                        else -> {
//                            val xCollision = Ranges().RangeContainsDirectional(Pos.x, size.x, xRange)
//                            if (xCollision != null) {
//
//                                return when (xCollision) {
//                                    1 -> {
//                                        Orientations.Left.Direction
//                                    }
//                                    -1 -> {
//                                        Orientations.Right.Direction
//                                    }
//                                    else -> {
//                                        Vector2D()
//                                    }
//                                }
//                            }
//                        }
//                    }


                //Collision on the X axis, hitting a wall


            }
            return collisionObject
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

    fun OutOfBoundCollision(gameController: GameController, w: Int, h: Int): Vector2D {
        if (Pos.x > w - size.x || Pos.x < 10) {

            // Vector2D(Direction.x * -1, Direction.y)
        }
        if (Pos.y > h - size.y || Pos.y < 10) {

            //Vector2D(Direction.x, Direction.y * -1)
        }
        return Vector2D()
    }


}