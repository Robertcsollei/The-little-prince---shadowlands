package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import org.pondar.pacmankotlin.GameEntities.GenerateEnemies
import org.pondar.pacmankotlin.Interfaces.Characters.Enemy
import org.pondar.pacmankotlin.Interfaces.Characters.PacMan
import org.pondar.pacmankotlin.Interfaces.Objects.GoldCoin
import java.util.ArrayList


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context,view: TextView) {

        val mainHandler = Handler(Looper.getMainLooper())



        private var pointsView: TextView = view
        private var points : Int = 0
        //bitmap of the pacman
        lateinit var PacMan: PacMan

        var enemyGenerator: GenerateEnemies = GenerateEnemies()

        var enemies: ArrayList<Enemy> = ArrayList()

        val updatePos = object : Runnable {
            override fun run() {
                PacMan.speed += 2

                mainHandler.postDelayed(this, 10)
            }
        }

        //did we initialize the coins?
        var coinsInitialized = false

        //the list of goldcoins - initially empty
        var coins = ArrayList<GoldCoin>()

        var coinRadius = 80.0

        var delCoin = -1
        //a reference to the gameview
        private var gameView: GameView? = null
         var h: Int = 0
         var w: Int = 0 //height and width of screen


    init {
        PacMan = PacMan(5, BitmapFactory.decodeResource(context.resources, R.drawable.pacman),
                        50.0,50.0)

    }

    fun setGameView(view: GameView) {
        this.gameView = view


    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        coinsInitialized = true


        if(coinsInitialized) {
            enemies = enemyGenerator.InitializeEnemies(2, context,
                    this.gameView?.w,
                    this.gameView?.h)
            var counter = 0

            var offsetX = 200
            var offsetY = 200

            for (x in 0..4) {
                for (y in 0..4) {
                    coins.add(GoldCoin(offsetX * x, offsetY * y, context, counter))
                    counter++
                }
            }
        }

    }


    fun newGame() {
        PacMan.posX = 50.0
        PacMan.posY = 400.0 //just some starting coordinates - you can change this.
        //reset the points
        coinsInitialized = false
        points = 0
        pointsView.text = "${context.resources.getString(R.string.points)} $points"
        gameView?.invalidate() //redraw screen
    }
    fun setSize(h: Int, w: Int) {
        this.h = h
        this.w = w
    }
    fun resetPos () {
        PacMan.posX = 50.0
        PacMan.posY = 50.0
        gameView!!.invalidate()
    }

    fun setPacPosition(ms: Int) {

        PacMan.keepMoving()

        enemies.forEach {
            it.move(1,1, this , gameView!! )
        }

        coins.forEachIndexed { index, goldCoin ->
            doCollisionCheck(goldCoin, index)
        }
        if(delCoin > 0){
            coins.removeAt(delCoin)
            delCoin = -1
        }
        gameView!!.invalidate()
        Log.d("Log", "gameView.toString()")

    }



    //TODO check if the pacman touches a gold coin
    //and if yes, then update the neccesseary data
    //for the gold coins and the points
    //so you need to go through the arraylist of goldcoins and
    //check each of them for a collision with the pacman
    fun doCollisionCheck(coin: GoldCoin, index: Int) {

        if(!coin.isCollected){
            if(PacMan.posX in coin.goldX- coinRadius .. coin.goldX+coinRadius && PacMan.posY in coin.goldY-coinRadius.. coin.goldY+coinRadius){
                coin.isCollected = true
                delCoin = index
                Log.d("Collected", coin.id.toString())

            }
        }

    }


}