package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import java.util.*


class MainActivity : AppCompatActivity(), View.OnTouchListener, SensorEventListener  {

    //reference to the game class.
    private var game: Game? = null

    val TimerFunction: Timer = Timer()

    var updateMS = 0;

    lateinit var sensorManager: SensorManager



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        game = Game(this,pointsView)

        gameView.setOnTouchListener(this)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME
        )

        game?.setGameView(gameView)
        gameView.setGame(game)
        game?.newGame()

        TimerFunction.schedule(object :TimerTask(){
            override fun run(){
                UpdateFunction()
            }
        }, 0, 10)




       // mainHandler.post(updatePos)



    }


    fun UpdateFunction() {
        this.runOnUiThread(Update)
    }

    val Update = Runnable {
        var playExplosion = game?.playExplosion
        updateMS += 10
        if(updateMS >= 200){
            updateMS = 0
            game?.setPacPosition(true)
        }

        if (updateMS >= 200 && playExplosion!!){
            game?.setPacPosition(false)
        }
        game?.setPacPosition(false)

        //Object Motion
        //Enemy Motion
        //Projectile Motion
        //Collision detection
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            Toast.makeText(this, "settings clicked", Toast.LENGTH_LONG).show()
            return true
        } else if (id == R.id.action_newGame) {
            Toast.makeText(this, "New Game clicked", Toast.LENGTH_LONG).show()
            game?.newGame()
            return true
        }
        return super.onOptionsItemSelected(item)
    }




    override fun onTouch(v: View?, event: MotionEvent?): Boolean {



        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                game?.PacMan?.Pos = Vector2D(game?.SpaceShip!!.Pos.x, game?.SpaceShip!!.Pos.y)
                game?.PacMan?.Initial = Vector2D(game?.SpaceShip!!.Pos.x, game?.SpaceShip!!.Pos.y)
                game?.PacMan?.isMoving = !game?.PacMan?.isMoving!!
            }
            MotionEvent.ACTION_MOVE -> {
                game?.PacMan?.Pos = Vector2D(game?.SpaceShip!!.Pos.x, game?.SpaceShip!!.Pos.y)
                game?.aimForm = Vector2D(game?.SpaceShip!!.Pos.x + 100, game?.SpaceShip!!.Pos.y + 100)
                game?.aimAt = Vector2D(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                game?.PacMan?.timer = 100
                game?.PacMan?.move( Vector2D(event.x, event.y) , game!!, gameView!!)

                game?.aimAt = Vector2D()
                game?.aimForm = Vector2D()


            }
        }
        gameView.invalidate()


        return true
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
      game?.ShipPos = event?.values?.get(0)!!
    }


}
