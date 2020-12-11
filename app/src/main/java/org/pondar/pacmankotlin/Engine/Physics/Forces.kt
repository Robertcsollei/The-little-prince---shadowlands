package org.pondar.pacmankotlin.Engine.Physics

import android.util.Log
import org.pondar.pacmankotlin.Engine.Adapters.Ranges
import org.pondar.pacmankotlin.Engine.Collections.Orientations
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Game.GameObjects.Wall
import org.pondar.pacmankotlin.GameController

class Forces(var gameController: GameController) {

    var checker = 0

    private fun applyGravity(Direction: Vector2D) {
        Direction.y += 0.01F
        //Direction.x -= 0.01F
    }

    private fun modifyDirection(Direction: Vector2D, Factor1: Float?, Factor2: Float?) {
        Direction.x += Factor1 ?: Direction.x
        Direction.y += Factor2 ?: Direction.y
    }

    private fun setDirection(Direction: Vector2D, Factor1: Float?, Factor2: Float?) {
        Direction.x = Factor1 ?: Direction.x
        Direction.y = Factor2 ?: Direction.y

    }

    fun gravity() {

        /*
        * in each wall
        * if it is colliding in any way
        * save the collision direction and the colliding obj
        * else return null
        *
        * */

        val player = gameController.Player
        val dir = player.Direction

        gameController.GameObjects.forEach {


            if (player.wallCollision.x != 0F && !player.onGround) {
                setDirection(player.Direction, 0F, 5F)
                Log.d("CollisionOnTheLEft", "Left")
            } else {
                if (!player.onGround) {
                    Log.d("Ground", "NoGround")
                    applyGravity(player.Direction)
                    player.falling = true

                } else {

                    player.Direction = Vector2D()
                    player.falling = false
                }
            }

            var collision: Collision = Collision(it, player.Pos, player.Size)

            val collDirection = collision.WallCollision()

            //All the static object which are NOT colliding will end up in this part of the check
            if (collDirection == null) {


                // NO COLLISION DETECTED...
            }
            //All the static object which ARE colliding
            else {

                if (collDirection.isEnemey) {
                    val enemy = gameController.GameObjects.find {
                        it == collDirection.target
                    }
                    if (enemy != null) {
                        if (player.strikeDir == 0) {
                            if (!enemy.dead) {
                                player.dead = true
                            }
                        } else {

                            enemy.dead = true

                            Log.d("CollisionOnTheLEftXXX", "Enemy killed")
                        }
                    }

                }

                //  COLLISION DETECTED!
                if (collDirection.ground) {


                    player.onGround = true
                    player.wallCollision = Vector2D()
                    checker = 0

                } else {


                    if (collDirection?.isEmpty) {

                        Log.d("Ground", "$collDirection")




                        player.onGround = false
                        player.wallCollision = Orientations.Left.Direction
                        player.jumpTimer = 2001


                    }


                    // Checking foe the Direction of the collision. The Orientation Direction refers to the normal of the Plane of the colliding box.
                    // Therefore always points away from the received direction. If I collide with the ground the collision direction -> the applicable force is upwards
//                        when {
//                            collDirection!!.Equals(Orientations.Up.Direction!!) -> {
//
//                                //player.onGround = true
//                            }
//                            collDirection.Equals(Orientations.Down.Direction!!) -> {
//                                Log.d("Dggrt", "Down")
//                            }
//                            collDirection.Equals(Orientations.Left.Direction!!) -> {
//                                Log.d("Dggrt", "Left")
//                            }
//                            collDirection.Equals(Orientations.Right.Direction!!) -> {
//                                Log.d("Dggrt", "Right")
//                            }
//                        }


                }


            }


        }

    }
}

