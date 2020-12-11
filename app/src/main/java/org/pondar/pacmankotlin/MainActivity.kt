package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.pondar.pacmankotlin.Game.Characters.Enemy
import org.pondar.pacmankotlin.Game.GameObjects.GoldCoin
import java.util.*
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.gesture.GestureOverlayView.OnGesturePerformedListener
import android.gesture.Gesture
import android.util.Log
import org.pondar.pacmankotlin.Engine.Physics.Forces
import org.pondar.pacmankotlin.Game.GameObjects.Wall
import org.pondar.pacmankotlin.Game.Input.Accelerometer
import org.pondar.pacmankotlin.Game.Input.TouchEvent

class MainActivity : AppCompatActivity(),  OnGesturePerformedListener  {

    var gLibrary: GestureLibrary?= null

    //reference to the game class.
    private var gameController: GameController? = null

    val TimerFunction: Timer = Timer()

    var updateMS = 0

    var updateLong = 0

    var isPaused = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_main)
        gameController = GameController(this)



        this.getSupportActionBar()?.hide();
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            actionBar?.hide()

        }


        gestureSetup()

        gameController?.setGameView(gameView)
        gameView.setGame(gameController)
        gameController?.newGame()

        //Registering listeners
        TouchEvent( gameController, gameView, this ).setListener()
        Accelerometer(gameController, this).setListener()


        TimerFunction.schedule(object :TimerTask(){
            override fun run(){
                UpdateFunction()
            }
        }, 0, 10)

    }

    private fun gestureSetup(){
        gLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture)
        if(gLibrary?.load() == false){
            finish()
        }
        gestureOverlay.addOnGesturePerformedListener(this)
    }

    fun pauseContinue(view: View){
        val buttonID = resources.getIdentifier("pauseBTN", "id", packageName)
        val button = findViewById<Button>(buttonID)
        if (!isPaused){
            button.text = "Continue"
            isPaused = true
        }else {
            button.text = "Pause"
            isPaused = false
        }
    }

    fun UpdateFunction() {

        if(!isPaused) {
            this.runOnUiThread(Update)
        }
    }

    val Update = Runnable {

        var playExplosion = gameController?.playExplosion
        updateMS += 10
        updateLong += 10

        if(updateMS >= 200){
            updateMS = 0
            gameController?.setPacPosition(true)


        }


        if(updateLong >= 200){
           // GravityForce(gameController?.w!! , gameController?.SpaceShip?.Pos!!, gameController?.SpaceShip?.Size!!, gameController!!).addGravity()
           // GravityForce(gameController?.GameObjects!!, gameController?.Player!!, gameController!!, updateLong).applyGravity()

//            if(gameController?.jump!!){
//                Force(gameController!!, updateLong).ApplyForce("right")
//            }


            Forces(gameController!!).gravity()
            if(gameController!!.Player.jump != 0F){
                gameController!!.Player.jump()
                Log.d("updateLong", gameController!!.Player.wallCollision.toString())

            }

            gameController!!.Player.Update()



                if (gameController?.projectile?.isShooting!!){
                    gameController?.projectile?.keepMoving()

                    gameController?.GameObjects!!.forEach{
                        if(it is GoldCoin){
                            it.keepMoving(gameController!!)

                        }
                    }

                    if(updateLong >= 8000){
                        updateLong = 0
                        gameController?.isShooting = false
                    }
            }
        }else{

        }

        if (updateMS >= 200 && playExplosion!!){
            gameController?.setPacPosition(false)
        }
        gameController?.setPacPosition(false)


        gameController?.GameObjects?.forEach {
            if(it is Enemy){

                it.keepMoving( gameController?.w!!, gameController?.h!!)

            }
        }

    }




    override fun onGesturePerformed(overlay: GestureOverlayView?, gesture: Gesture?) {
        val predictions = gLibrary?.recognize(gesture)

        predictions?.let{
            if(it.size > 0 && it[0].score > 1.0){
                val action = it[0].name
                Toast.makeText(this, action,Toast.LENGTH_SHORT).show()
                when ((it[0].name)) {

                    "rightToLeft" -> {
                        gameController?.Player!!.strikeDir = 1
                    }
                    "rightHook" -> {
                        gameController?.Player!!.strikeDir = 1
                    }
                }
            }
        }

    }


}


