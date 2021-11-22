package com.example.platformer



import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.SystemClock.uptimeMillis
import android.view.SurfaceView
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import kotlin.random.Random

const val STAGE_WIDTH=1080
const val STAGE_HEIGHT=720

val  RNG= Random(uptimeMillis())
lateinit var engine:Game

private const val TAG= "Game"
const val PREFS="com.example.platformer.sw"
const val pixelsPerMeter=50

class Game(context: Context) : SurfaceView(context) ,Runnable,SurfaceHolder.Callback{
    private var gameThread=Thread(this)



    @Volatile
    private var isRunning=false
    private var isGameOver=false
    private var levelManager=LevelManager(TestLevel())

    val paint=Paint()
    init{
        engine=this

        holder.addCallback(this)  //
        holder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT)

    }


    fun worldToScreenX(worldDistance:Float)=(worldDistance*pixelsPerMeter).toInt()
    fun worldToScreenY(worldDistance:Float)=(worldDistance*pixelsPerMeter).toInt()
    fun screenToWorldX(pixelDistance:Float)= (pixelDistance/pixelsPerMeter)
    fun screenToWorldY(pixelDistance:Float)= (pixelDistance/pixelsPerMeter)
    override fun run() {
        //while isRunning
        //input , not needed, controlled by events
        //update
        //render
        while(isRunning){
            update()
            render()
        }


    }

    private fun update() {
     //  levelManager.update(dt)  ToDo


    }






    /* override fun onTouchEvent(event: MotionEvent?): Boolean {  //from UIthread=need to be volatile
         return super.onTouchEvent(event)
     }*/
    //from week 2 Touch, Multi....
    //@Volatile var isBoosting=false made available for update


    private fun render(){
        //lock and acquire surface
        /*val holder= holder ?: return
        val surface= holder.surface ?: return
        if(!surface.isValid()){
            return}*/


        val canvas=aquireAndLockCanvas() ?: return//lockHardwareCanvas uses GPU
        canvas.drawColor(Color.BLUE)
        val paint=Paint()
        for (e in levelManager.entities){
            e.render(canvas,paint)
        }



        holder.unlockCanvasAndPost(canvas)  //release canvas, adapt to screen  frame rate
        //draw the game world to the surface
        //unlock and post surface to the UI thread

    }



    private fun aquireAndLockCanvas(): Canvas? {
        if(holder?.surface?.isValid==false)
            return null
        else return holder.lockCanvas()


    }

    fun resume() {
        Log.d(TAG,"resume")
        isRunning=true


        //create thread start the work
    }

    fun pause() {
        Log.d(TAG,"pause")
        isRunning=false
        gameThread.join()

        //join thread stop work
    }



    override fun surfaceCreated(p0: SurfaceHolder) {

        Log.d(TAG,"surfaceCreated")

        if((gameThread.state==Thread.State.NEW)) {
            Log.d(TAG,"Thread not running,start thread")
            gameThread.start()


        }else {
            Log.d(TAG,"Thread not new")
            Log.d(TAG,gameThread.state.toString())
            if(gameThread.state==Thread.State.TERMINATED){
                Log.d(TAG,"Thread terminated due to pause, create new thread")
                gameThread= Thread(this)
                gameThread.start()
            }


        }
    }
    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
        //override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        Log.d(TAG,"surfaceChanged,width:$width,height:$height")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        Log.d(TAG,"surfaceDestroyed")
    }





}