package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import org.pondar.pacmankotlin.Interfaces.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Interfaces.DataTypes.Shape2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D


//note we now create our own view class that extends the built-in View class
class GameView : View {

    private var game: Game? = null
    var h: Int = 0
    var w: Int = 0 //used for storing our height and width of the view

    var ResizeBitmap: BitMapConverter = BitMapConverter()

    var newMatrix = Matrix()

    fun setGame(game: Game?) {
        this.game = game
    }
    var background =  BitmapFactory.decodeResource(context.resources, R.drawable.bg)
    val newbg = ResizeBitmap.resizeBitmap(background, 2500)

    /* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    @SuppressLint("DrawAllocation")
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

        canvas.drawBitmap(newbg, 0F, 0F, paint) //clear entire canvas to white color

        val mPath: Path
        mPath = Path()
        mPath.moveTo(game!!.aim().shape.left, game!!.aim().shape.top)
        mPath.quadTo(game!!.aim().shape.left, game!!.aim().shape.top ,game!!.aim().shape.top, game!!.aim().shape.bottom )

        fun drawLine(shape: Shape2D){

            return canvas.drawLine(shape.left, shape.top, shape.right, shape.bottom, paint)
        }
        fun drawRect(shape: Shape2D){

            return canvas.drawRect(shape.left, shape.top, shape.right, shape.bottom, paint)
        }



        var BitSize = Vector2D(game?.fireBall!!.bitmap!!.width.toFloat(), game?.fireBall!!.bitmap!!.height.toFloat())

        var pacBit = Bitmap.createBitmap(game?.fireBall!!.bitmap!!,0, 0, BitSize.x.toInt(), BitSize.y.toInt(), newMatrix, true)
        canvas.drawBitmap(pacBit, game?.fireBall!!.Pos.x, game?.fireBall!!.Pos.y, paint)


       if(game?.isShooting!!){


           var projectMatrix = Matrix()
           projectMatrix.postRotate(90F)

           var projectileMap = Bitmap.createBitmap(game?.projectile!!.bitmap!!,0, 0,
                   w / 5 / 2, ((w / 5 / 2) * 0.2).toInt(), projectMatrix, true)

           canvas.drawBitmap(projectileMap, game?.projectile!!.Pos.x, game?.projectile!!.Pos.y, paint)
       }

        canvas.drawBitmap(game?.SpaceShip?.bitmap!!, game?.SpaceShip?.Pos!!.x, game?.SpaceShip?.Pos!!.y, paint )
        if (game?.playExplosion!!) {
            canvas.drawBitmap(game?.Explosion?.bitmap!!, game?.Explosion?.Pos!!.x, game?.Explosion?.Pos!!.y, paint)
        }

        if(game!!.coinsInitialized){


            for(GameEntity in game?.GameObjects!!){
                if(GameEntity.bitmap != null){
                canvas.drawBitmap(GameEntity.bitmap!!, GameEntity.Pos.x,
                        GameEntity.Pos.y, paint)
                }else{
                    paint.setColor(Color.DKGRAY);
                    drawRect(GameEntity.shape)
                }

            }
            paint.setAntiAlias(true);
            paint.setStrokeWidth(6f);

            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setPathEffect( DashPathEffect(floatArrayOf(5F, 10F), 0F))
            drawLine(game!!.aim().shape)

            // Initialize Enemies
        }



        //TODO loop through the list of goldcoins and draw them.


        super.onDraw(canvas)
    }


}
