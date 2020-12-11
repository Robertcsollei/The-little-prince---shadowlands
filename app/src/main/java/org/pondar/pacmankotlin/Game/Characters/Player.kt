package org.pondar.pacmankotlin.Game.Characters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.Adapters.Ranges
import org.pondar.pacmankotlin.Engine.Collections.Orientations
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Engine.Interfaces.ICharacter
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Sprites
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.R
import kotlin.collections.ArrayList

class Player(override val life: Int, override var shape: Shape2D, override var bitmap: Bitmap?,
             override var Xunit: Int, override var Yunit: Int, var context: Context, var gameController: GameController) : Object2D, ICharacter {


    var screenWidth = 550;
    override var speed = 2.0F
    override fun move(initial: Vector2D, gameController: GameController, view: View) {


    }

    override var isStatic: Boolean = false
    override var isCollectable: Boolean = false
    override var isCollected: Boolean = false
    override var Pos: Vector2D = shape.pos
    override var Size: Vector2D = Vector2D(200F, 209F)
    override var Initial: Vector2D = Vector2D()
    override var Direction: Vector2D = Vector2D()
    override var ResizeBitmap: BitMapConverter = BitMapConverter()
    override var VertexMatrix: ArrayList<ArrayList<Vector2D>>? = null
    override var isGround: Boolean = false
    var wallCollision = Vector2D()
    var falling = false
    var anims: Sprites
    var glitch = false
    override var dead = false

    var animtimer = 0
    var timer = 0

    var strikeDir = 0

    var walk = 0F

    var index = 0

    var movingForward: Boolean = true
    override var BoundingBox: BoundingBox? = BoundingBox(shape.pos.x, shape.pos.y, shape.size.x, shape.size.y)
    var jumpTimer = 0
    var jump = 0F
    var onGround = false

    init {
        anims = Sprites()
        anims.idle = arrayListOf(R.drawable.adventurer_idle_2_01, R.drawable.adventurer_idle_2_02, R.drawable.adventurer_idle_2_03)
        anims.landing = arrayListOf(R.drawable.adventurer_crouch_02)
        anims.falling = arrayListOf(R.drawable.adventurer_fall_00, R.drawable.adventurer_fall_01)
        anims.attack = arrayListOf(R.drawable.adventurer_attack1_00, R.drawable.adventurer_attack1_01, R.drawable.adventurer_attack1_02, R.drawable.adventurer_attack1_03, R.drawable.adventurer_attack1_04,
                R.drawable.adventurer_attack1_01)
        anims.death = arrayListOf()
        anims.running = arrayListOf(R.drawable.adventurer_run_00, R.drawable.adventurer_run_01, R.drawable.adventurer_run_02)
        anims.ability1 = arrayListOf()
        anims.ability2 = arrayListOf()
    }

    fun Walk(walk: Float) {
        Direction = Vector2D(walk, 0F)
    }

    fun keepMoving(AccePos: Float, width: Int, Height: Int) {

        if (AccePos in -8F..12F) {
            var newPos = width / 2 - AccePos * width / 2 / 12
            // Pos = Vector2D(floor(newPos.toDouble()).toInt().toFloat(), (Height / 8 * 7).toFloat())

        }


    }

    fun strike() {
        if (strikeDir != 0 && wallCollision.Equals(Vector2D())) {

            timer++

            if (timer < 60) {
                if (timer <= 30) {
                    Log.d("ghakbfjd", wallCollision.toString())
                    Direction = Vector2D(20F, 0F)
                } else {
                    Direction = Vector2D(-20F, 0F)
                }

            } else {
                timer = 0
                strikeDir = 0
            }

        }

    }


    fun jump(): Boolean {
//        var switchAllowedDirections = ((jump > 0F && !wallCollision.Equals(Orientations.Right.Direction)) || (jump < 0F && !wallCollision.Equals(Orientations.Left.Direction)) || wallCollision.Equals(Vector2D()))
//
//        Log.d("HELLOTHEGGGG", "${wallCollision}")
//        if (switchAllowedDirections) {


        if (jumpTimer <= 2000) {
            jumpTimer++
        }


        if (jumpTimer < 10) {
            Direction.y = -500 / jumpTimer.toFloat() * 0.1F
            Direction.x = 1F
            onGround = false
        } else {

            jump = 0F

        }
//        }
//
//    else{
//            jump = 0F
//        }
        return true
    }

    fun Update() {


        if (Pos.y > 8 * Yunit) {
            dead = true
        }

        if (walk != 0F) {
            Walk(walk)
        }

        strike()


        Pos.x += Direction.x
        Pos.y += Direction.y
        if (dead) {
            gameController.newGame()
        }

        if (falling) {
            this.bitmap = BitmapFactory.decodeResource(context.resources, anims.falling?.get(0)!!)
        } else if (strikeDir != 0) {
            animtimer++
            if (animtimer % 10 == 0) {
                this.bitmap = BitmapFactory.decodeResource(context.resources, anims.attack?.get(index)!!)
                index++
                if (index == 6) {
                    index = 0
                }
            }
        } else {
            animtimer++
            if (animtimer < 30 && this.bitmap != BitmapFactory.decodeResource(context.resources, anims.landing?.get(0)!!)) {

                this.bitmap = BitmapFactory.decodeResource(context.resources, anims.landing?.get(0)!!)
            } else {
                if (animtimer % 10 == 0) {

                    this.bitmap = BitmapFactory.decodeResource(context.resources, anims.running?.get(index)!!)
                    index++
                    if (index == 3) {
                        index = 0
                    }

                }

            }

        }

        VertexMatrix = Ranges().CreateVertexMatrix(Pos.x, Pos.x + Size.x, Pos.y, Pos.y + Size.y)
    }


    override fun OnCollison() {

    }

}