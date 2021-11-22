package com.example.platformer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class StaticEntity(spriteName:String,x:Float,y:Float) : Entity(){
    lateinit var bitmap:Bitmap
    init{
        this.x=x
        this.y=y
        var width = 1.0f
        var height = 1.0f
        val widthInPixels=engine.worldToScreenX(width)
        val heightInPixels=engine.worldToScreenX(height)

        bitmap=BitmapUtils.loadScaledBitmap(engine.context,spriteName,widthInPixels
            ,heightInPixels)

    }

    override fun render(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmap,
            engine.worldToScreenX(x).toFloat(),
            engine.worldToScreenY(y).toFloat(),
            paint)


    }
}