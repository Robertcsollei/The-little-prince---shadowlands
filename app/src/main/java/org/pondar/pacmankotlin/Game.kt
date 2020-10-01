package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import java.lang.Math.floor
import java.lang.Math.pow
import java.util.ArrayList
import kotlin.math.sqrt


/**
 *
 * This class should contain all your game logic
 */

class Game(private var context: Context,view: TextView) {

        val mainHandler = Handler(Looper.getMainLooper())



        private var pointsView: TextView = view
        private var points : Int = 0
        //bitmap of the pacman
        var pacBitmap: Bitmap
        var pacx: Double = 0.0
        var pacy: Double = 0.0

        var InitialX = 0
        var InitialY = 0

        var EndX = 0
        var EndY = 0

        var speed = 50

        var acceleration:Double = 2.0
        var accSpeed = 5.0

        var descSpeed = 0.5

        val updatePos = object : Runnable {
            override fun run() {
                speed += 2

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
        pacBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman)

    }

    fun accelerate(){
        acceleration *= accSpeed
    }

    fun normalize() {
        if(acceleration > 0.0){
            acceleration -= descSpeed
        }
        if(acceleration < 0.0){
            acceleration += descSpeed
        }
    }

    fun setGameView(view: GameView) {
        this.gameView = view
    }

    //TODO initialize goldcoins also here
    fun initializeGoldcoins() {
        coinsInitialized = true

        if(coinsInitialized) {

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
        pacx = 50.0
        pacy = 400.0 //just some starting coordinates - you can change this.
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
        pacx = 50.0
        pacy = 50.0
        gameView!!.invalidate()
    }


    fun movePacmanRight(pixels: Int) {
        //still within our boundaries?


        if (pacx + pixels + pacBitmap.width < w) {
            acceleration += accSpeed
            var newSpeed = pixels + acceleration
            Log.d("speed", "$acceleration $accSpeed")
            pacx += newSpeed.toInt()

            gameView!!.invalidate()
        }

    }

    fun setPacPosition(x: Int, y: Int) {
        if(!(EndX in 50 downTo -50 && EndY in 50 downTo -50)) {
            var len = sqrt(pow(EndX.toDouble(), 2.0) + pow(EndY.toDouble(), 2.0))
            Log.d("MATRIX", len.toString() + " " + (EndX / len).toString())
            pacx += EndX / len * speed
            pacy += EndY / len * speed
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
            if(pacx in coin.goldX- coinRadius .. coin.goldX+coinRadius && pacy in coin.goldY-coinRadius.. coin.goldY+coinRadius){
                coin.isCollected = true
                delCoin = index
                Log.d("Collected", coin.id.toString())

            }
        }

    }


}