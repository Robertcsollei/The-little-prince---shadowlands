package org.pondar.pacmankotlin

import android.content.Context
import android.util.Log
import android.widget.TextView
import org.pondar.pacmankotlin.GameEntities.GenerateObjects
import org.pondar.pacmankotlin.Interfaces.Characters.Enemy
import org.pondar.pacmankotlin.Interfaces.Characters.FireBall
import org.pondar.pacmankotlin.Interfaces.Characters.SpaceShip
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.GameActions.Collider
import org.pondar.pacmankotlin.Interfaces.GameActions.Exposion
import org.pondar.pacmankotlin.Interfaces.Objects.Projectile
import org.pondar.pacmankotlin.Interfaces.Objects.Wall
import java.util.*


/**
 *
 * This class should contain all your game logic
 */

class Game(var context: Context, view: TextView) {


    private var pointsView: TextView = view
    private var points: Int = 0

    //bitmap of the pacman
    lateinit var fireBall: FireBall
    lateinit var SpaceShip: SpaceShip

    var GenerateObjects = GenerateObjects(context, 0, 0)


    var GameObjects = ArrayList<Object2D>()

    var coinsInitialized = false

    var counter = 0


    var aimForm = Vector2D()
    var aimAt = Vector2D()

    var delCoin = -1

    var ShipPos = 0F

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

    init {
        fireBall = GenerateObjects.InitPlayer()
        SpaceShip = GenerateObjects.InitShip()

    }

    fun setGameView(view: GameView) {
        this.gameView = view


    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        coinsInitialized = true


        if (coinsInitialized) {
            GenerateObjects = GenerateObjects(context, w, h)

            GameObjects = GenerateObjects.InitEnvironment()
            Enemies = GenerateObjects.Enemies
            randomPos = randomEnemyShooting()
            randomEnemy = Enemies.elementAt(randomPos)

                if(!isShooting){
                    projectile = Projectile(randomEnemy.Pos, w/5, h/8, context, this)
                    isShooting = true
                    projectile.isShooting = true
                }


            Log.d("JKFHJHKD", randomEnemyShooting().toString())
//            randomEnemy = Enemies.get(randomEnemyShooting())

        }

    }

    fun randomEnemyShooting(): Int{
        //if ms % 200 0 - Rando Bger 0,5
        var Random = Random()
        return Random.nextInt(Enemies.count())
    }




    fun newGame() {

        //reset the points
        isShooting = false
        projectile =  Projectile(Vector2D(500F, 500F), w/5, h/8, context, null)
        coinsInitialized = false
        fireBall.Initial = Vector2D()
        fireBall.Direction = Vector2D()
        fireBall.Pos = Vector2D(SpaceShip.Pos.x + 50, SpaceShip.Pos.y + 50)
        points = 0
        counter = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
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

        return Wall(context, Shape2D(aimForm, aimAt, 1))
    }


    fun setPacPosition(invodeSprite: Boolean) {

        GameObjects.forEachIndexed { index, gameEntity ->
            doCollisionCheck(gameEntity, index)
        }
//
//        projectile = Projectile(randomEnemy.Pos, randomEnemy.Xunit, randomEnemy.Yunit, context)
//
//        if(invodeSprite){
//            projectile.isShooting = true
//        }



        fireBall.keepMoving(w, h, GameObjects, context, invodeSprite, SpriteImages, this)

        SpaceShip.keepMoving(ShipPos, w, h)

        if (playExplosion) {
            Explosion.Explode(ExplosionPos, this, context)

        }

        if (delCoin >= 0) {
            ExplosionPos = GameObjects[delCoin].Pos

            GameObjects.removeAt(delCoin)

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
        var collider = Collider(Object, fireBall.Pos, Object.Size)

        if (Object.isCollectable) {
            collider = Collider(Object, SpaceShip.Pos, Object.Size)

            if (!Object.isCollected) {

                if (collider.isColliding()) {
                    Object.OnCollison()
                    delCoin = index
                    counter++
                    pointsView.text = "${context.resources.getString(R.string.points)} $counter"
                    Log.d("COOLSS", delCoin.toString())


                }
            }
        } else if (!Object.isCollectable && !Object.isStatic) {

            if (collider.isColliding()) {
                Object.OnCollison()
                delCoin = index
            }

        }


    }


}