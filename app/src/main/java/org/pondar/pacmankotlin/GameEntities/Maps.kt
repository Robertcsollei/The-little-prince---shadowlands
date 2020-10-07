package org.pondar.pacmankotlin.GameEntities

class Maps (){



    var level01 = arrayListOf(
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,3,0,1,0),
            arrayListOf(0,0,0,1,0),
            arrayListOf(0,0,0,1,0),
            arrayListOf(0,1,0,0,1),
            arrayListOf(0,0,0,0,1),
            arrayListOf(0,0,0,2,0),
            arrayListOf(0,0,0,0,0))

    var level02 = arrayListOf(
            arrayListOf(0,0,0,0,2),
            arrayListOf(1,0,0,0,0),
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,0,1,0,0),
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,0,0,0,0))




    fun getMap(map: Int): ArrayList<ArrayList<Int>> {
       var Maps = arrayListOf(level01, level02)

        return Maps.elementAt(map)
    }
}