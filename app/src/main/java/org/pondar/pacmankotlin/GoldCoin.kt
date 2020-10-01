package org.pondar.pacmankotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class GoldCoin(xPos: Int, yPos: Int, context: Context, var id: Int) {



    var goldX = xPos
    var goldY = yPos
    var goldBitmap: Bitmap
    var isCollected = false

    init{
       goldBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coin)
    }




}