package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener  {

    //reference to the game class.
    private var game: Game? = null

    val TimerFunction: Timer = Timer()

    var updateMS = 10;

    var switchedDirection : Boolean = false



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        game = Game(this,pointsView)

        gameView.setOnTouchListener(this)


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
        //TODO
        //Player Motion
        game?.setPacPosition()
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


    override fun onClick(view : View?){




    }



    override fun onTouch(v: View?, event: MotionEvent?): Boolean {



        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                game?.aimForm = Vector2D(game?.PacMan?.Pos!!.x + 75, game?.PacMan?.Pos!!.y +75)
                game?.PacMan?.Initial = Vector2D(event.x, event.y)
                game?.PacMan?.isMoving = !game?.PacMan?.isMoving!!
            }
            MotionEvent.ACTION_MOVE -> {

                game?.aimAt = Vector2D(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                game?.PacMan?.timer = 100
                game?.aimAt = Vector2D()
                game?.aimForm = Vector2D()
                game?.PacMan?.move( Vector2D(event.x, event.y) , game!!, gameView!!)


            }
        }
        gameView.invalidate()


        return true
    }




}
