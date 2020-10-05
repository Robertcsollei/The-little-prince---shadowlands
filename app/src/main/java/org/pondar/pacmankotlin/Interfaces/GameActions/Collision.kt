package org.pondar.pacmankotlin.Interfaces.GameActions

import org.pondar.pacmankotlin.Interfaces.Characters.ICharacter
import org.pondar.pacmankotlin.Interfaces.Objects.GoldCoin
import java.util.*
import kotlin.collections.ArrayList

abstract class Collision(var posX: Int, var PosY: Int)  {
    var isCollider: Boolean = true

    fun CollisionCheck(detectedItems: ArrayList<ICharacter>){
        // TODO implement

    }
}