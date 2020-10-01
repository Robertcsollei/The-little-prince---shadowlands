package org.pondar.pacmankotlin

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener  {

    //reference to the game class.
    private var game: Game? = null
    val mainHandler = Handler(Looper.getMainLooper())

    var isMovign = 0
    var comparee = 0

    val updatePos = object : Runnable {
        override fun run() {
            if(comparee > 0){
                comparee--
                game?.normalize()
                Log.d("Move", isMovign.toString() + "HE IS HERE")
            }
            mainHandler.postDelayed(this, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        game = Game(this,pointsView)


        game?.setGameView(gameView)
        gameView.setGame(game)
        game?.newGame()
//
//        reset.setOnClickListener { game?.resetPos() }
//
//        moveRight.setOnTouchListener(RepeatListener(10, 5, View.OnClickListener {
//            isMovign++
//            comparee = isMovign
//            game?.coins?.forEach { coin -> game?.doCollisionCheck(coin) }
//           // Log.d("MESSAGE", isMovign.toString())
//            game?.movePacmanRight(3)
//        }))




       // mainHandler.post(updatePos)



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

        if(view?.id == R.id.moveRight){
            mainHandler.post(updatePos)

        }


    }





}
