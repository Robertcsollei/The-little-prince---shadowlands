package org.pondar.pacmankotlin.Engine.Physics

import android.graphics.BitmapFactory
import android.util.Log
import org.pondar.pacmankotlin.Engine.Collections.Orientations
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Game.Characters.Player
import org.pondar.pacmankotlin.Game.GameObjects.Wall
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.R

class Forces(var gameController: GameController) {


    fun directionalForce( orientation: Vector2D){

    gameController.Player.Direction.x = orientation.x
    gameController.Player.Direction.y = orientation.y

    }

    fun gravity() {


        gameController.GameObjects.forEach {

            if (it is Wall) {

                var collision: Collision = Collision(it, gameController.Player.Pos, gameController.Player.Size)

                val collDirection = collision.WallCollision()

                if (collDirection != Orientations.Down.Direction && gameController.gravityOn) {

                  gameController.Player.Direction.y += 0.01F
                }
                else{

                    if(collDirection == Orientations.Down.Direction){

                                gameController.Player.onGround = true
                            }
                    if(gameController.Player.Direction.y != 0F && gameController.gravityOn){
                       gameController.gravityOn = false
                       gameController.Player.Direction.y = 0F
                    }

                }


            }
        }
    }

}

