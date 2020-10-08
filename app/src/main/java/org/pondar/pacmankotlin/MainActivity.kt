package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.intellij.lang.annotations.Flow
import org.pondar.pacmankotlin.Interfaces.Characters.Enemy
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import org.pondar.pacmankotlin.Interfaces.Objects.Projectile
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), View.OnTouchListener, SensorEventListener  {

    //reference to the game class.
    private var game: Game? = null

    val TimerFunction: Timer = Timer()

    var updateMS = 0

    var updateLong = 0

    lateinit var sensorManager: SensorManager

    var SensorData : ArrayList<Float> = ArrayList()



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
        updateLong += 10
        if(updateMS >= 200){
            updateMS = 0
            game?.setPacPosition(true)
        }

        if(updateLong >= 200){

                if (game?.projectile?.isShooting!!){
                    game?.projectile?.keepMoving()

                    if(updateLong >= 8000){
                        updateLong = 0
                        game?.isShooting = false
                    }
            }
        }

        if (updateMS >= 200 && playExplosion!!){
            game?.setPacPosition(false)
        }
        game?.setPacPosition(false)


        game?.GameObjects?.forEach {
            if(it is Enemy){

                it.keepMoving( game?.w!!, game?.h!!)

            }
        }

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
                game?.fireBall?.Pos = Vector2D(game?.SpaceShip!!.Pos.x + game?.fireBall!!.Resize, game?.SpaceShip!!.Pos.y + game?.fireBall!!.Resize)
                game?.fireBall?.Initial = Vector2D(game?.SpaceShip!!.Pos.x, game?.SpaceShip!!.Pos.y)
                game?.fireBall?.isMoving = !game?.fireBall?.isMoving!!
            }
            MotionEvent.ACTION_MOVE -> {
                game?.fireBall?.Pos = Vector2D(game?.SpaceShip!!.Pos.x + game?.SpaceShip!!.Size.x/2, game?.SpaceShip!!.Pos.y + game?.SpaceShip!!.Size.y/2)
                game?.aimForm = Vector2D(game?.SpaceShip!!.Pos.x + game?.SpaceShip!!.Size.x/2, game?.SpaceShip!!.Pos.y + game?.SpaceShip!!.Size.y/2)
                game?.aimAt = Vector2D(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                game?.fireBall?.timer = gameView.width/7
                game?.fireBall?.move( Vector2D(event.x, event.y) , game!!, gameView!!)

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
        SensorData.add(event?.values?.get(0)!!)

        if (SensorData.count() > 30){
            var avgArray : List<Float> = SensorData.drop(SensorData.count() -29)
            var avg = avgArray.average()

            game?.ShipPos = avg.toFloat()
        }
        else {
            game?.ShipPos = event?.values?.get(0)!!
        }
    }


}
