package org.pondar.pacmankotlin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.DatatTypes.Shape2D
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D
import kotlin.concurrent.thread


//note we now create our own view class that extends the built-in View class
class GameView : View {

    private var gameController: GameController? = null
    var h: Int = 0
    var w: Int = 0 //used for storing our height and width of the view

    var ResizeBitmap: BitMapConverter = BitMapConverter()


    var newMatrix = Matrix()

    fun setGame(gameController: GameController?) {
        this.gameController = gameController
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
        h = canvas.width
        w = canvas.height
        //update the size for the canvas to the game.
        gameController?.setSize(h, w)


        //are the coins initiazlied?
        if (!(gameController!!.coinsInitialized))
            gameController?.initializeGoldcoins()


        //Making a new paint object
        val paint = Paint()

        canvas.drawBitmap(newbg, 0F, 0F, paint) //clear entire canvas to white color

        val mPath: Path
        mPath = Path()
        mPath.moveTo(gameController!!.aim().shape.left, gameController!!.aim().shape.top)
        mPath.quadTo(gameController!!.aim().shape.left, gameController!!.aim().shape.top, gameController!!.aim().shape.top, gameController!!.aim().shape.bottom)

        fun drawLine(shape: Shape2D) {

            return canvas.drawLine(shape.left, shape.top, shape.right, shape.bottom, paint)
        }

        fun drawRect(shape: Shape2D) {

            return canvas.drawRect(shape.left, shape.top, shape.right, shape.bottom, paint)
        }

//
//
//        var BitSize = Vector2D(gameController?.fireBall!!.bitmap!!.width.toFloat(), gameController?.fireBall!!.bitmap!!.height.toFloat())
//
//        var pacBit = Bitmap.createBitmap(gameController?.fireBall!!.bitmap!!,0, 0, BitSize.x.toInt(), BitSize.y.toInt(), newMatrix, true)
//        canvas.drawBitmap(pacBit, gameController?.fireBall!!.Pos.x, gameController?.fireBall!!.Pos.y, paint)
//
//
//       if(gameController?.isShooting!!){
//
//
//           var projectMatrix = Matrix()
//           projectMatrix.postRotate(90F)
//
//           var projectileMap = Bitmap.createBitmap(gameController?.projectile!!.bitmap!!,0, 0,
//                   w / 5 / 3, ((w / 5 / 3) * 0.2).toInt(), projectMatrix, true)
//
//           canvas.drawBitmap(projectileMap, gameController?.projectile!!.Pos.x, gameController?.projectile!!.Pos.y, paint)
//       }
//
        canvas.drawBitmap(gameController?.Player?.bitmap!!, gameController?.Player?.Pos!!.x, gameController?.Player?.Pos!!.y, paint)



            if (gameController!!.coinsInitialized) {
                for(GameEntity in gameController?.GameObjects!!){
                    if(GameEntity.bitmap == null){
                        paint.setColor(Color.DKGRAY);
                        canvas.drawRect(GameEntity.shape.left, GameEntity.shape.top, GameEntity.shape.right, GameEntity.shape.bottom, paint)

                    }else{
                        canvas.drawBitmap(GameEntity.bitmap!!, GameEntity.Pos!!.x, GameEntity.Pos!!.y, paint)
                    }

                }

////
////            for(GameEntity in gameController?.GameObjects!!){
////                if(GameEntity.bitmap != null){
////                canvas.drawBitmap(GameEntity.bitmap!!, GameEntity.Pos.x,
////                        GameEntity.Pos.y, paint)
////                }else{
////                    paint.setColor(Color.DKGRAY);
////                    drawRect(GameEntity.shape)
////                }
////
////            }
//            paint.setAntiAlias(true);
//            paint.setStrokeWidth(6f);
//
//            paint.setColor(Color.WHITE);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeJoin(Paint.Join.ROUND);
//            paint.setPathEffect( DashPathEffect(floatArrayOf(5F, 10F), 0F))
//            drawLine(gameController!!.aim().shape)

                // Initialize Enemies
            }


            //TODO loop through the list of goldcoins and draw them.


            super.onDraw(canvas)
        }


}
