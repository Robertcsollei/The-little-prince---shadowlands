package org.pondar.pacmankotlin.Interfaces.Characters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.GameActions.Collider
import org.pondar.pacmankotlin.R
import kotlin.math.PI
import kotlin.math.cos

class PacMan(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?) : ICharacter, Object2D {

    var isMoving = false
    var acceleration:Double = 0.1
//    var accSpeed = 5.0
    var SwipeTrashhold = 50
    var descSpeed = 0.5
    override var speed = 15.0F
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(bitmap?.width?.toFloat()!!, bitmap?.height?.toFloat()!!)
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    var timer: Int = 0
    var temptimer= 0
    var reset = 1F
    var NewAngle = 0F
    var switchImage = 0

    override fun move(EndPos: Vector2D, game: Game, view: View){
       Direction = game.aimAt.Substract(game.aimForm)
Log.d("AAASSSDD", "${Direction.x} - ${Direction.y}")

        if(isMoving) {
            game.setPacPosition(false)
        }
        view.invalidate()
    }

    fun keepMoving(w: Int, h: Int, GameObejct: ArrayList<Object2D>, context: Context ,invodeSprite: Boolean, SpriteImages: ArrayList<Int>, game: Game ){
        if(!(Direction.x.toInt() in SwipeTrashhold downTo -SwipeTrashhold && Direction.y.toInt() in SwipeTrashhold downTo -SwipeTrashhold)) {



            GameObejct.forEach{



                if(it.isCollectable){

                    if(it.isCollected) {

                        if(!(Pos.x in it.Pos.x - 10 .. it.Pos.x + 50)){
                            Direction = Vector2D(Direction.x * -1, Direction.y )
                        }else{

                            Direction = Vector2D(Direction.x, Direction.y * -1)
                        }

                    }
                }
                if(it.isStatic && !it.isCollectable){

                    if ((Pos.x + 50 in it.Pos.x - 50..  it.Pos.x + 150) && (Pos.y + 50 in it.Pos.y- 50 .. it.Pos.y + 150)) {

                            Direction = Vector2D(Direction.x * -1, Direction.y )


                            Direction = Vector2D(Direction.x, Direction.y * -1)

                    }

                }
            }

            //Walls collision
            if(Pos.x > w - 140|| Pos.x < 10){
                Direction = Vector2D(Direction.x * -1, Direction.y )

            }
            if(Pos.y > h - 140|| Pos.y < 10){
                Direction = Vector2D(Direction.x, Direction.y * -1)

            }



            //Acceleration / Decrease
            if(temptimer < timer){
                reset = 1F
                temptimer++
                speed += acceleration.toFloat()
            }
            if(temptimer > timer){
                if(temptimer < timer + 100 && reset > 0){
                    reset -= 0.01F
                    if(temptimer == timer +1){
                        reset = 0F
                        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.empty)
                    }
                }
                temptimer--
                speed -= acceleration.toFloat()
            }
            if(temptimer == timer && timer != 0 && temptimer != 0){
                timer = 0
                reset = 1F
            }

            //Set Direction
            Log.d("MATRIIIXX", "${timer} - ${temptimer}")
            var Direct = Direction.Normalize(speed * reset)


            var a = Direction.Normalize(1F)
            var b = Vector2D(0F,1F)
            var a0 = a.x* b.x + a.y * b.y


            var oneSide = a0 / (a.Length() * b.Length())

            Log.d("NEWMATRIX", oneSide.toString())

            fun hack(degree: Float): Int{
                if(Pos.x > w / 2){
                    if(degree < 0){
                        return -2
                    }
                    if(degree > 0) {
                        return 2
                    }
                    else {
                        return 0
                    }
                }else{
                    if(degree < 0){
                        return 2
                    }
                    if(degree > 0) {
                        return -2
                    }
                    else {
                        return 0
                    }
                }
            }

            if(invodeSprite){
                if(switchImage < 8) {
                    bitmap = BitmapFactory.decodeResource(context.resources, SpriteImages.elementAt(switchImage))
                }else{
                    switchImage = 0
                }
                switchImage++
            }


            //Apply Direction
            Pos.x += Direct.x
            Pos.y += Direct.y


            //NewAngle = (oneSide * 60 /  2 * PI   ).toFloat()
            Log.d("GDGFHGJTYD", NewAngle.toString())





        }
    }

//    fun accelerate(){
//        acceleration *= accSpeed
//    }
//
//    fun normalize() {
//        if(acceleration > 0.0){
//            acceleration -= descSpeed
//        }
//        if(acceleration < 0.0){
//            acceleration += descSpeed
//        }
//    }

    override fun OnCollison() {

    }

}