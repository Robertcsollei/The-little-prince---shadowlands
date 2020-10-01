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
class GameView : View , View.OnTouchListener{

    private var game: Game? = null
    private var h: Int = 0
    private var w: Int = 0 //used for storing our height and width of the view

    val mainHandler = Handler(Looper.getMainLooper())

    val updatePos = object : Runnable {
        override fun run() {
            game?.speed = game?.speed?.plus(2)!!
            Log.d("MATRIX", game?.speed.toString())
            invalidate()

            mainHandler.postDelayed(this, 10)
        }
    }

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

        this.setOnTouchListener(this)

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
        canvas.drawBitmap(game!!.pacBitmap, game?.pacx!!.toFloat(),
                game?.pacy!!.toFloat(), paint)


        if(game!!.coinsInitialized){
            for(coin in game?.coins!!){
                canvas.drawBitmap(coin!!.goldBitmap, coin?.goldX!!.toFloat(),
                        coin?.goldY!!.toFloat(), paint)
            }
        }

        //TODO loop through the list of goldcoins and draw them.


        super.onDraw(canvas)
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        val x = event!!.x.toInt() -40
        val y = event.y.toInt() -40

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                game?.InitialX = x
                game?.InitialY = y
            }
            MotionEvent.ACTION_MOVE -> Log.d("", "")
            MotionEvent.ACTION_UP -> {
               game?.EndX = x - game?.InitialX!!
                game?.EndY = y - game?.InitialY!!
                Log.i("TAG", "${game?.EndX} ${game?.EndY}")

            game?.setPacPosition(x , y)

                //mainHandler.post(updatePos)
            }
        }
        invalidate()


        return true
    }
}
