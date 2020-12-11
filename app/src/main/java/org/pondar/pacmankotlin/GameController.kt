package org.pondar.pacmankotlin

import android.content.Context
import android.util.Log
import org.pondar.pacmankotlin.Engine.Colliders.BoundingBox
import org.pondar.pacmankotlin.Game.Generators.GenerateObjects
import org.pondar.pacmankotlin.Game.Characters.Enemy
import org.pondar.pacmankotlin.Game.GameObjects.FireBall
import org.pondar.pacmankotlin.Game.Characters.Player
import org.pondar.pacmankotlin.Engine.Interfaces.Object2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import org.pondar.pacmankotlin.Engine.Physics.Collision
import org.pondar.pacmankotlin.Engine.Physics.Exposion
import org.pondar.pacmankotlin.Game.GameObjects.Projectile
import org.pondar.pacmankotlin.Game.GameObjects.Wall
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 * This class should contain all your game logic
 */

class GameController(var context: Context) {
    //Level number to load different maps

    private var points: Int = 0

    //bitmap of the pacman
    lateinit var fireBall: FireBall
    lateinit var Player: Player

    var GenerateObjects = GenerateObjects(context, 0, 0, this)

    var BoundingBoxArray: ArrayList<BoundingBox> = arrayListOf(BoundingBox(150F,150F, 550F, 250F))

    var gravityOn = true
    var ForceFrameTimer = 0
    var Momentum = Vector2D()
    var colliding = false

    var fps: ArrayList<Byte> = ArrayList<Byte>()

    var GameObjects = ArrayList<Object2D>()

    var coinsInitialized = false

    var counter = 0

    var timeElapsed = 1F

    var aimForm = Vector2D()
    var aimAt = Vector2D()

    var delCoin = -1

    var ShipPos = 0F

    var mapSpped = 2F

    var SpriteImages = arrayListOf<Int>(R.drawable.frame1, R.drawable.frame2, R.drawable.frame3,
            R.drawable.frame4, R.drawable.frame5, R.drawable.frame6, R.drawable.frame7, R.drawable.frame8)

    var Explosion: Exposion = Exposion(context)
    lateinit var ExplosionPos: Vector2D
    var playExplosion: Boolean = false

    //a reference to the gameview
    private var gameView: GameView? = null
    var h: Int = 0
    var w: Int = 0 //height and width of screen

    var Enemies: ArrayList<Enemy> = ArrayList()
    lateinit var randomEnemy : Enemy
    var randomPos = -1

    var isShooting = false

    var projectile = Projectile(Vector2D(500F, 500F), w/5, h/8, context, null)

    var isNewLevel : Boolean = false
    var mapNo: Int = 0

    init {
        fireBall = GenerateObjects.InitFireBall()
        Player = GenerateObjects.InitPlayer()

    }

    fun setGameView(view: GameView) {
        this.gameView = view


    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        coinsInitialized = true


        if (coinsInitialized) {
            GenerateObjects = GenerateObjects(context, w, h, this)

            LoadNewLevel(mapNo)
            Enemies = GenerateObjects.Enemies



            fireBall = GenerateObjects.InitFireBall()
            Player = GenerateObjects.InitPlayer()





//            randomEnemy = Enemies.get(randomEnemyShooting())

        }

    }



    fun LoadNewLevel(levelNo: Int){

        GameObjects = if (mapNo > 0){

            GenerateObjects.InitEnvironment(levelNo)


        } else {
            GenerateObjects.InitEnvironment(levelNo)
        }

        fireBall.Initial = Vector2D()
        fireBall.Direction = Vector2D()
        fireBall.Pos = Vector2D(Player.Pos.x + 50, Player.Pos.y + 50)
    }


    fun newGame() {

        //reset the points
        isShooting = false
        projectile =  Projectile(Vector2D(500F, 500F), w/5, h/8, context, null)
        coinsInitialized = false
        fireBall.Initial = Vector2D()
        fireBall.Direction = Vector2D()
        fireBall.Pos = Vector2D(Player.Pos.x + 50, Player.Pos.y + 50)
        points = 0
        counter = 0

        gameView?.invalidate() //redraw screen
    }

    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }

    fun resetPos() {
        fireBall.Pos = Vector2D(200F, 800F)
        gameView!!.invalidate()
    }


    fun aim(): Wall {
        return Wall(context, Shape2D(aimForm, aimAt, 1), BoundingBox(true), false)
    }


    fun setPacPosition(invodeSprite: Boolean) {

        GameObjects.forEachIndexed { index, gameEntity ->
            doCollisionCheck(gameEntity, index)
        }

        fireBall.keepMoving(w, h, GameObjects, context, invodeSprite, SpriteImages, this)

        Player.keepMoving(ShipPos, w, h)



        GameObjects.forEach{
            it.shape.left -= mapSpped
            it.shape.right -= mapSpped
            it.BoundingBox?.Pos!!.x -= mapSpped
            if(it.BoundingBox!!.Pos.x < -5750) {
                newGame()
            }
            if(it is Enemy) {
                it.Update()
            }

        }

        if (playExplosion) {
            Explosion.Explode(ExplosionPos, this, context)

        }

        if (delCoin >= 0) {

            ExplosionPos = GameObjects[delCoin].Pos

            GameObjects.removeAt(delCoin)
            if (isNewLevel){
                if (mapNo == 3){
                    LoadNewLevel(0)
                    isNewLevel = false
                    mapNo = 0
                }else {
                    LoadNewLevel(mapNo)
                    isNewLevel = false
                }


            }

            delCoin = -1
            playExplosion = true
        }

        gameView!!.invalidate()

    }


    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck(Object: Object2D, index: Int) {
        var collider = Collision(Object, fireBall.Pos, Object.Size)

        if (Object.isCollectable) {
            collider = Collision(Object, Player.Pos, Object.Size)

            if (!Object.isCollected) {

                if (collider.isColliding()) {
                    Object.OnCollison()
                    delCoin = index
                  //  Log.d("COOLSS", delCoin.toString())
                    newGame()


                }
            }
        } else if (!Object.isCollectable && !Object.isStatic) {

            if (collider.isColliding()) {
                Object.OnCollison()
                delCoin = index
                counter++

                if (counter >= Enemies.count() && mapNo < 3){
                  //  Log.d("GAMELOGIC", "IF")

                    mapNo ++
                    counter = 0
                    isNewLevel = true
                }
            }

        }


    }


}