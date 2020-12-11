package org.pondar.pacmankotlin.Game.Characters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.Interfaces.ICharacter
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Sprites
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.R

class Enemy(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?,
            override var Xunit: Int, override var Yunit: Int,  var context: Context) : ICharacter, Object2D {

    var newSize : Int = Xunit

    var screenWidth = 550;
    override var speed = 2.0F
    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(newSize.toFloat(), newSize.toFloat())
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    override var VertexMatrix: ArrayList<ArrayList<Vector2D>>? = null
    override var isGround: Boolean = false
    override var dead = false
    var isShooting: Boolean = false
    var Matrix: Matrix = Matrix()
    var movingForward: Boolean = true
    var isDestroyed : Boolean = false
    var anims: Sprites
    var index = 0


    var animtimer = 0

    override var BoundingBox: BoundingBox? = BoundingBox(shape.pos.x, shape.pos.y, shape.size.x, shape.size.y)

    init {


        anims = Sprites()
        anims.idle = arrayListOf(R.drawable.skeleton_idle_01, R.drawable.skeleton_idle_02, R.drawable.skeleton_idle_03, R.drawable.skeleton_idle_04)
        anims.attack = arrayListOf(R.drawable.skeleton_attack_02, R.drawable.skeleton_attack_03, R.drawable.skeleton_attack_04,
                R.drawable.skeleton_attack_05, R.drawable.skeleton_attack_06, R.drawable.skeleton_attack_07)
        anims.death = arrayListOf(R.drawable.skeleton_death_01, R.drawable.skeleton_death_02, R.drawable.skeleton_death_03, R.drawable.skeleton_death_04,
                R.drawable.skeleton_death_05, R.drawable.skeleton_death_06 , R.drawable.skeleton_death_07)

    }


    fun Update() {

        Pos.x -= 2F

        animtimer++
        if(!dead){
            if(animtimer % 10 == 0) {
                this.bitmap =  BitmapFactory.decodeResource(context.resources, anims.idle?.get(0)!!)
                index++
                if(index == 4) {
                    index = 0
                }
            }

        }else{
            if(animtimer % 10 == 0) {
                this.bitmap =  BitmapFactory.decodeResource(context.resources, anims.death?.get(index)!!)

                if(index < 6) {
                    index++
                }
            }

        }

    }

    override fun move(Initialize: Vector2D, gameController: GameController, view: View) {

//        if (movingForward){
//            Pos.x += speed
//
//            //isShooting = Pos.x % 35 == 0.0
//
//            if (Pos.x >= screenWidth-newSize)
//                movingForward = false
//        }
//        else {
//            Pos.x -= speed
//            if(Pos.x <= newSize)
//                movingForward = true
//        }
    }

    fun keepMoving(w: Int, h: Int){
//        if (movingForward){
//            Pos.x += speed
//
//            //isShooting = Pos.x % 35 == 0.0
//
//            if (Pos.x >= w-newSize*2)
//                movingForward = false
//        }
//        else {
//            Pos.x -= speed
//            if(Pos.x <= newSize)
//                movingForward = true
//        }
    }

    override fun OnCollison() {
        this.isDestroyed = true
    }


}