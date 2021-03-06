package com.example.platformer



import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.core.view.WindowCompat

private const val TAG="GameActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var  game : Game
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        game=Game (this)
        setContentView(this.game)
        Log.d(TAG,"On Create Called")
    }

    override fun onPause() {
        game.pause()
        super.onPause()
    }  //ctrl O

    override fun onResume() {

        super.onResume()
        game.resume()
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) { //handle older SDKs, using the deprecated systemUiVisbility API
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        } else {
            // Tell the Window that our app is going to responsible for fitting for any system windows.
            // This is similar to: view.setSystemUiVisibility(LAYOUT_STABLE | LAYOUT_FULLSCREEN)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            //getWindowInsetsController from our root View, the game:
            val controller = game.windowInsetsController
            // Hide the keyboard (IME = "input method editor")
            controller?.hide(WindowInsets.Type.ime())
            // Sticky Immersive Mode is now written:
            controller?.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // hide the all the system bars:
            controller?.hide(WindowInsets.Type.systemBars())
            val flag = WindowInsets.Type.statusBars()
            WindowInsets.Type.navigationBars()
            WindowInsets.Type.captionBar()
            window?.insetsController?.hide(flag)
        }
    }
}

