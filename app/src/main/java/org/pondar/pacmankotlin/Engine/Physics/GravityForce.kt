package org.pondar.pacmankotlin.Engine.Physics

import android.graphics.BitmapFactory
import android.util.Log
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Game.Characters.Player
import org.pondar.pacmankotlin.Game.GameObjects.Wall
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.R

class GravityForce(var GameObjects: ArrayList<Object2D>, var player: Player, var gameController: GameController, var updateLong: Int) {
    var falling = false
    var playAnim = 0


    fun applyGravity(){
//
//        GameObjects.forEach {
//
//            if(it is Wall){
//
//                var collision: Collision = Collision(it, player.Pos, player.Size)
//
//                if(!collision.WallCollision() && gameController.gravityOn){
//
//                   if(!falling){
//                       falling = true
//                       gameController.Player.bitmap = BitmapFactory.decodeResource(gameController.context.resources, R.drawable.adventurer_test)
//                   }
//                    gameController.ForceFrameTimer++
//
//                    player.Direction.y += 0.01F
//
//
//                }else{
//                    gameController.colliding = true
//                    if(falling){
//                        falling = false
//                        gameController.Player.bitmap = BitmapFactory.decodeResource(gameController.context.resources, R.drawable.adventurer_inpact)
//                    }
//                    gameController.ForceFrameTimer = 0
//                    if(gameController.gravityOn){
//                        player.Direction.y = 0.0F
//
//                    }
//                    Log.d("grwavityCheck", player.Direction.y.toString())
//                    gameController.gravityOn = false
//
//                }
//            }
//        }
//
//        if(!falling){
//            player.Direction.y = 0.0F
//            if(updateLong % 400 == 0){
//                gameController.Player.bitmap = BitmapFactory.decodeResource(gameController.context.resources, R.drawable.adventurer_idle)
//            }
//        }
    }

}