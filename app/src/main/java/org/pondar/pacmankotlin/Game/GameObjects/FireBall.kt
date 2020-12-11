package org.pondar.pacmankotlin.Game.GameObjects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.Interfaces.ICharacter
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Physics.Collision

class FireBall(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?, override var Xunit: Int, override var Yunit: Int) : ICharacter, Object2D {


    var isMoving = true
    var acceleration:Double = 0.1
    var Resize = 12
    var SwipeTrashhold = 50
    var descSpeed = 0.5
    override var dead = false
    override var speed = 15.0F
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(Resize.toFloat(),Resize.toFloat())
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    override var VertexMatrix: ArrayList<ArrayList<Vector2D>>? = null
    override var isGround: Boolean = false
    var timer: Int = 0
    var temptimer= 0
    var reset = 1F
    var NewAngle = 0F
    var switchImage = 0
    var NewSize = 150
    override var BoundingBox: BoundingBox? = BoundingBox(shape.pos.x, shape.pos.y, shape.size.x, shape.size.y)


    init {
        bitmap = ResizeBitmap.resizeBitmap(bitmap!!, 12)
    }


    override fun move(EndPos: Vector2D, gameController: GameController, view: View){
       Direction = gameController.aimAt.Substract(gameController.aimForm)
        NewSize = view.height/12

        if(isMoving) {
            gameController.setPacPosition(false)
        }
        view.invalidate()
    }

    fun keepMoving(w: Int, h: Int, GameObejct: ArrayList<Object2D>, context: Context, invodeSprite: Boolean, SpriteImages: ArrayList<Int>, gameController: GameController ){
        if(!(Direction.x.toInt() in SwipeTrashhold downTo -SwipeTrashhold && Direction.y.toInt() in SwipeTrashhold downTo -SwipeTrashhold)) {



            GameObejct.forEach{
                var collision: Collision = Collision(it, Pos, Direction)


            }

            //Out of Bounds
           var BoundsCollider = Collision(this, Pos, Size)




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






        }
    }


    override fun OnCollison() {

    }

}