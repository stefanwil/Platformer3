package com.example.platformer

class TestLevel : LevelData (){
    init {
        tilesToBitmap.put(NO_TILE,"no_tile")
        tilesToBitmap.put(1,PLAYER)
        tilesToBitmap.put(2,"zigzaggrass_2roundleft.png")
        tilesToBitmap.put(3,"zigzaggrass_2roundright.png")
        tilesToBitmap.put(4,"zigzaggrass_squar.png")

        tiles = arrayOf(
            intArrayOf(2,3,4,0,1))
        updateLevelDimensions()
    }


}