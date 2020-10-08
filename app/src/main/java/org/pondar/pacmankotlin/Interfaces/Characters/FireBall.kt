package org.pondar.pacmankotlin.Interfaces.Characters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Game
import org.pondar.pacmankotlin.Interfaces.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.GameActions.Collider
import org.pondar.pacmankotlin.R
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos

class FireBall(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?, override var Xunit: Int, override var Yunit: Int) : ICharacter, Object2D {

    var isMoving = true
    var acceleration:Double = 0.1
    var Resize = 12
    var SwipeTrashhold = 50
    var descSpeed = 0.5
    override var speed = 15.0F
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(Resize.toFloat(),Resize.toFloat())
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    var timer: Int = 0
    var temptimer= 0
    var reset = 1F
    var NewAngle = 0F
    var switchImage = 0
    var NewSize = 150


    init {
        bitmap = ResizeBitmap.resizeBitmap(bitmap!!, 12)
    }


    override fun move(EndPos: Vector2D, game: Game, view: View){
       Direction = game.aimAt.Substract(game.aimForm)
        NewSize = view.height/12

        if(isMoving) {
            game.setPacPosition(false)
        }
        view.invalidate()
    }

    fun keepMoving(w: Int, h: Int, GameObejct: ArrayList<Object2D>, context: Context ,invodeSprite: Boolean, SpriteImages: ArrayList<Int>, game: Game ){
        if(!(Direction.x.toInt() in SwipeTrashhold downTo -SwipeTrashhold && Direction.y.toInt() in SwipeTrashhold downTo -SwipeTrashhold)) {



            GameObejct.forEach{
                var collider: Collider = Collider(it, Pos, Direction)
                Direction = collider.GeneralColision()

            }

            //Out of Bounds
           var BoundsCollider = Collider(this, Pos, Size)
            BoundsCollider.OutOfBoundCollision(game, w, h)



            //Acceleration / Decrease
            if(temptimer < timer){
                reset = 1F
                temptimer++
                speed += acceleration.toFloat()
                isMoving = true
            }
            if(temptimer > timer){
                isMoving = true
                if(temptimer < timer + 100 && reset > 0){
                    reset -= 0.01F

                    if(temptimer == timer +1){
                        reset = 0F
                       isMoving = false
                    }
                }

                temptimer--
                speed -= acceleration.toFloat()
            }
            if(temptimer == timer && timer != 0 && temptimer != 0){
                timer = 0
                reset = 1F
                isMoving = false
            }

            //Set Direction

            var Direct = Direction.Normalize(speed * reset)


            var a = Direction.Normalize(1F)

            var b = Vector2D(1F,0F)

            var t = Vector2D(0F,1F)

            var p = Vector2D(0.5F,0.5F)

            var dotProduct = a.dotProduct(b)

            var dott = t.dotProduct(b)

            var dotp = p.dotProduct(b)

            var newAngle = dotProduct / (a.Length() * b.Length())

            var newAngle1 = dott / (t.Length() * b.Length())

            var newAngle2 = dotp / (p.Length() * b.Length())


            Log.d("ANGLESXXX", newAngle.toString() + " X")

            Log.d("ANGLESXXX", newAngle1.toString() + " Y")

            Log.d("ANGLESXXX", newAngle2.toString() + " 45")

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
                    var newBit = BitmapFactory.decodeResource(context.resources, SpriteImages.elementAt(switchImage))
                    bitmap = ResizeBitmap.resizeBitmap(newBit, NewSize)
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


    override fun OnCollison() {

    }

}