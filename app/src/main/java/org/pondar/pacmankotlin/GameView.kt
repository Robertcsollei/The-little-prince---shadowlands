package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


//note we now create our own view class that extends the built-in View class
class GameView : View {

    private var game: Game? = null
    var h: Int = 0
    var w: Int = 0 //used for storing our height and width of the view


    fun setGame(game: Game?) {
        this.game = game
    }


    /* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    override fun onDraw(canvas: Canvas) {



        //Here we get the height and weight
        h = canvas.height
        w = canvas.width
        //update the size for the canvas to the game.
        game?.setSize(h, w)


        //are the coins initiazlied?
        if (!(game!!.coinsInitialized))
            game?.initializeGoldcoins()




        //Making a new paint object
        val paint = Paint()
        canvas.drawColor(Color.WHITE) //clear entire canvas to white color

        //draw the pacman
        canvas.drawBitmap(game!!.PacMan.picture, game?.PacMan?.posX!!.toFloat(),
                game?.PacMan?.posY!!.toFloat(), paint)


        if(game!!.coinsInitialized){
            for(coin in game?.coins!!){
                canvas.drawBitmap(coin!!.goldBitmap, coin?.goldX!!.toFloat(),
                        coin?.goldY!!.toFloat(), paint)
            }
            // Initialize Enemies
            for (enemy in game?.enemies!!){
                canvas.drawBitmap(enemy.picture, enemy.posX.toFloat(), enemy.posY.toFloat(), paint)
            }
        }

        //TODO loop through the list of goldcoins and draw them.


        super.onDraw(canvas)
    }


}
