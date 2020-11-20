package org.pondar.pacmankotlin.Engine.Adapters

import org.pondar.pacmankotlin.Engine.DatatTypes.Vector2D

class Ranges {

    fun RangeContains(OneDPosition: Float, OneDSize: Float , Range: ClosedFloatingPointRange<Float>): Boolean{
        return (OneDPosition in Range || OneDPosition + OneDSize in Range)
    }


    fun RangeContainsEither(Pos: Vector2D, size: Vector2D, xRange:ClosedFloatingPointRange<Float>, yRange: ClosedFloatingPointRange<Float> ): Boolean{
        return RangeContains(Pos.x, size.x, xRange) && RangeContains(Pos.y, size.y, yRange)
    }


    fun CreateVertexArray(Min:Float, Max: Float, offset: Float): ArrayList<Vector2D>{
        var VertexArray: ArrayList<Vector2D> = ArrayList<Vector2D>()
        for(i in Min.toInt() .. Max.toInt()){
            VertexArray.add(Vector2D(i.toFloat(), offset))
        }
        return VertexArray
    }

    fun CreateVertexMatrix(xMin: Float, xMax: Float, yMin: Float, yMax: Float): ArrayList<ArrayList<Vector2D>>{
        val VertexMatrix: ArrayList<ArrayList<Vector2D>> = ArrayList<ArrayList<Vector2D>>()

        for(x in 2..5){
            if(x % 2 == 0){
                if(x == 2){
                    VertexMatrix.add(CreateVertexArray(xMin, xMax, yMin))
                }else{
                    VertexMatrix.add(CreateVertexArray(xMin, xMax, yMax))
                }
            }else{
                if(x == 3){
                    VertexMatrix.add(CreateVertexArray(yMin, yMax, xMin))
                }else{
                    VertexMatrix.add(CreateVertexArray(yMin, yMax, xMax))
                }

            }
        }
        return VertexMatrix
    }

}