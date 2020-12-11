package org.pondar.pacmankotlin.Engine.DatatTypes

import org.pondar.pacmankotlin.Engine.Interfaces.Object2D

class CollisionObject() {

    var ground: Boolean = false
    var target: Object2D? = null
    var collisionDirections: ArrayList<Vector2D> = ArrayList<Vector2D>()
    var ReachableBoxDetected = false
    var isEnemey = false
    var isEmpty = false

    fun noCollision(): Boolean {
        return ground || collisionDirections.count() != 0
    }
    fun has(vct: Vector2D): Boolean {
        return collisionDirections.contains(vct)
    }

    override fun toString(): String {
        return "ground: ${ground}, target: $target , collDir: $collisionDirections , Reachable Bounding Boxes detected: $ReachableBoxDetected, enemy: $isEnemey  "
    }

}