package org.pondar.pacmankotlin.GameEntities

class Maps (){

    var level01 = arrayListOf(
            arrayListOf(3,3,3,3,3),
            arrayListOf(3,0,2,0,3),
            arrayListOf(3,0,0,0,3),
            arrayListOf(3,0,0,0,3),
            arrayListOf(3,1,0,1,3),
            arrayListOf(2,0,0,0,3),
            arrayListOf(3,0,0,0,3),
            arrayListOf(0,0,0,0,0))

    var level02 = arrayListOf(
            arrayListOf(3,0,2,0,3),
            arrayListOf(3,0,1,0,3),
            arrayListOf(3,0,0,0,3),
            arrayListOf(3,1,0,0,3),
            arrayListOf(3,0,1,0,3),
            arrayListOf(3,0,0,0,3),
            arrayListOf(3,0,1,0,3),
            arrayListOf(0,0,0,0,0))

    var level03 = arrayListOf(
            arrayListOf(0,0,1,0,0),
            arrayListOf(0,2,0,1,0),
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,2,0,1,0),
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,0,0,2,0),
            arrayListOf(0,0,0,0,0),
            arrayListOf(0,0,0,0,0))




    fun getMap(map: Int): ArrayList<ArrayList<Int>> {
       var Maps = arrayListOf(level01, level02, level03)

        return Maps.elementAt(map)
    }
}