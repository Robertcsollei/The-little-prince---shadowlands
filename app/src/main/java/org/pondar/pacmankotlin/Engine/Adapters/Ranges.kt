package org.pondar.pacmankotlin.Engine.Adapters

import android.util.Log
import org.pondar.pacmankotlin.Engine.Collections.Orientations
import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D

class Ranges {

    fun RangeContains(OneDPosition: Float, OneDSize: Float, Range: ClosedFloatingPointRange<Float>): Boolean {
        return (OneDPosition in Range || OneDPosition + OneDSize in Range)
    }


    fun RangeContainsBoth(Pos: Vector2D, size: Vector2D, xRange: ClosedFloatingPointRange<Float>, yRange: ClosedFloatingPointRange<Float>): Boolean {
        return RangeContains(Pos.x, size.x, xRange) && RangeContains(Pos.y, size.y, yRange)
    }

    fun RangeContainsDirectional(OneDPosition: Float, OneDSize: Float, Range: ClosedFloatingPointRange<Float>): Int? {
        if(OneDPosition in Range && OneDPosition + OneDSize in Range){
            return 0
        }
        else if(OneDPosition in Range) {
            return -1
        }
        else if(OneDPosition + OneDSize in Range){
            return 1
        }
        return null
    }

    fun CreateVertexArray(Min: Float, Max: Float, offset: Float): ArrayList<Vector2D> {
        var VertexArray: ArrayList<Vector2D> = ArrayList<Vector2D>()
        for (i in Min.toInt()..Max.toInt()) {
            VertexArray.add(Vector2D(i.toFloat(), offset))
        }
        return VertexArray
    }

    fun CreateVertexMatrix(xMin: Float, xMax: Float, yMin: Float, yMax: Float): ArrayList<ArrayList<Vector2D>> {
        val VertexMatrix: ArrayList<ArrayList<Vector2D>> = ArrayList<ArrayList<Vector2D>>()

        for (x in 2..5) {
            if (x % 2 == 0) {
                if (x == 2) {
                    VertexMatrix.add(CreateVertexArray(xMin, xMax, yMin))
                } else {
                    VertexMatrix.add(CreateVertexArray(xMin, xMax, yMax))
                }
            } else {
                if (x == 3) {
                    VertexMatrix.add(CreateVertexArray(yMin, yMax, xMin))
                } else {
                    VertexMatrix.add(CreateVertexArray(yMin, yMax, xMax))
                }

            }
        }
        return VertexMatrix
    }

    private fun pointIsIntriangle(P: Vector2D, A: Vector2D, B: Vector2D, C: Vector2D): Boolean {
        val v = (A.x * (C.y - A.y) + (P.y - A.y) * (C.x - A.x) - P.x * (C.y - A.y)) / ((B.y - A.y) * (C.x - A.x) - (B.x - A.x) * (C.y - A.y))
        val u = (P.y - A.y - v * (B.y - A.y)) / (C.y - A.y)

        return v >= 0 && u >= 0 && (v + u <= 1)
    }

    fun collisionDirection(Point: Vector2D, xMin: Float, xMax: Float, yMin: Float, yMax: Float): Vector2D {


        val center = Vector2D(((xMax - xMin) / 2) + xMin, ((yMax - yMin) / 2) + yMin)

        val triangle1 = arrayOf(Vector2D(xMin, yMin), Vector2D(xMax, yMin), center)
        val triangle2 = arrayOf(Vector2D(xMax, yMin), Vector2D(xMax, yMax), center)
        val triangle3 = arrayOf(Vector2D(xMax, yMax), Vector2D(xMin, yMax), center)
        val triangle4 = arrayOf(Vector2D(xMin, yMax), Vector2D(xMin, yMin), center)

        var triangles = arrayOf(triangle1, triangle2, triangle3, triangle4)

        triangles.forEachIndexed { index, triangle ->
            if (pointIsIntriangle(Point, triangle[0], triangle[1], triangle[2])) {
                when (index) {
                    0 -> {
                        return Orientations.Up.Direction
                    }
                    1 -> {
                        return Orientations.Left.Direction
                    }
                    2 -> {
                        return Orientations.Down.Direction
                    }
                    3 -> {
                       return Orientations.Right.Direction
                    }
                }
            }
        }

        return Vector2D()
    }

}