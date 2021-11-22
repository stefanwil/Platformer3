package com.example.platformer

import android.util.SparseArray

internal const val PLAYER ="blue_left1"
internal const val NULLSPRITE ="nullsprite"
internal const val NO_TILE=0

abstract class LevelData {
    var tiles: Array<IntArray> = emptyArray()
    val tilesToBitmap = SparseArray<String>()

    var width = 0
    var height = 0
    fun getRow(y: Int): IntArray {
        return tiles[y]

    }

    fun getTile(x: Int, y: Int): Int {
        var yarray = getRow(y)
        return yarray[x]
        //       return getRow(y)[x]
    }

    fun getSpriteName(tileType: Int): String {
        val fileName = tilesToBitmap[tileType]
        return fileName ?: NULLSPRITE
    }
   @JvmName("getHeight1")
   fun getHeight():Int{
      return tiles.size
    }
    @JvmName("getWidth1")
    fun getWidth():Int {
        return tiles[0].size
    }

    fun updateLevelDimensions() {
        height = tiles.size
        width = getRow(0).size
    }
}

