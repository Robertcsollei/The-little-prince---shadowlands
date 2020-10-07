package org.pondar.pacmankotlin.Interfaces.GameActions

import org.pondar.pacmankotlin.Interfaces.DataTypes.Object2D
import org.pondar.pacmankotlin.Interfaces.DataTypes.Vector2D

class Collider (Object: Object2D, pacPos: Vector2D, size: Vector2D){

    var pacPos = pacPos
    var Object: Object2D = Object
    var Xmid = size.x
    var Ymid = size.y

    fun isColliding(): Boolean{
        return pacPos.x in Object.Pos.x - Xmid..Object.Pos.x + Xmid && pacPos.y in Object.Pos.y - Ymid..Object.Pos.y + Ymid
    }
}