package org.pondar.pacmankotlin.Interfaces.Objects

class Projectile {

    var posX: Int = 0
    var posY: Int = 0
    var speed: Int = 4

    fun initPos(x:Int, y:Int){
        posX = x
        posY = y
    }

    fun move(){
        posY += speed
    }
}