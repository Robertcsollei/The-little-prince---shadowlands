package org.pondar.pacmankotlin

import android.content.Context
import android.util.Log
import android.widget.Space
import android.widget.TextView
import org.pondar.pacmankotlin.GameEntities.GenerateObjects
import org.pondar.pacmankotlin.Interfaces.Characters.Enemy
import org.pondar.pacmankotlin.Interfaces.Characters.PacMan
import org.pondar.pacmankotlin.Interfaces.Characters.SpaceShip
import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.GameActions.Collider
import org.pondar.pacmankotlin.Interfaces.GameActions.Exposion
import org.pondar.pacmankotlin.Interfaces.Objects.GoldCoin
import org.pondar.pacmankotlin.Interfaces.Objects.Wall
import java.util.ArrayList


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context, view: TextView) {


    private var pointsView: TextView = view
    private var points: Int = 0

    //bitmap of the pacman
    lateinit var PacMan: PacMan
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
    var ExplosionPos = Vector2D()
    var playExplosion: Boolean = false

    //a reference to the gameview
    private var gameView: GameView? = null
    var h: Int = 0
    var w: Int = 0 //height and width of screen

    init {
        PacMan = GenerateObjects.InitPlayer()
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


        }

    }


    fun newGame() {

        //reset the points
        coinsInitialized = false
        PacMan.Initial = Vector2D()
        PacMan.Direction = Vector2D()
        PacMan.Pos = Vector2D(200F, 900F)
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
        PacMan.Pos = Vector2D(200F, 800F)
        gameView!!.invalidate()
    }


    fun aim(): Wall {

        return Wall(context, Shape2D(aimForm, aimAt, 1))
    }

    fun setPacPosition(invodeSprite: Boolean) {


        GameObjects.forEachIndexed { index, goldCoin ->
            doCollisionCheck(goldCoin, index)
        }

        PacMan.keepMoving(w, h, GameObjects, context, invodeSprite, SpriteImages, this)
        SpaceShip.keepMoving(ShipPos, w )

        if (playExplosion){
            Explosion.Explode(2,ExplosionPos, this, context)

        }

        if (delCoin > 0) {
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
        var collider = Collider(Object, PacMan.Pos, Object.Size)

        if (Object.isCollectable) {

            if (!Object.isCollected) {

                if (collider.isColliding()) {
                    Object.OnCollison()
                    delCoin = index
                    counter++
                    pointsView.text = "${context.resources.getString(R.string.points)} $counter"
                    Log.d("COOLSS", delCoin.toString())


                }
            }
        } else {


//            if (collider.isColliding()) {
//                GesturePos = Vector2D()
//            }
        }


    }


}