package org.pondar.pacmankotlin.Engine.Interfaces

import android.view.View
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.Engine.Adapters.BitMapConverter
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D

interface ICharacter {

    var Xunit: Int
    var Yunit :Int
    val life: Int
    var Initial: Vector2D
    var Direction: Vector2D
    var speed : Float
    var ResizeBitmap: BitMapConverter

    fun move(initial: Vector2D, gameController: GameController, view: View)



}