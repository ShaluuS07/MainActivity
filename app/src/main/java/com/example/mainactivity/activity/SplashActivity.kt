package com.example.mainactivity.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.mainactivity.R

class SplashActivity : AppCompatActivity() {

    private var navigated = false

    private val navigateRunnable = Runnable { goToMain() }

    private lateinit var splashImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()

        splashImage = findViewById(R.id.splashImage)
        splashImage.alpha = 0f
        splashImage.scaleX = INITIAL_SCALE
        splashImage.scaleY = INITIAL_SCALE

        val alphaIn = ObjectAnimator.ofFloat(splashImage, View.ALPHA, 0f, 1f)
        val scaleXIn = ObjectAnimator.ofFloat(splashImage, View.SCALE_X, INITIAL_SCALE, 1f)
        val scaleYIn = ObjectAnimator.ofFloat(splashImage, View.SCALE_Y, INITIAL_SCALE, 1f)
        AnimatorSet().apply {
            duration = ENTRANCE_DURATION_MS
            interpolator = DecelerateInterpolator()
            playTogether(alphaIn, scaleXIn, scaleYIn)
            start()
        }

        splashImage.postDelayed(navigateRunnable, ENTRANCE_DURATION_MS + HOLD_AFTER_ENTRANCE_MS)
        splashImage.postDelayed(navigateRunnable, MAX_SPLASH_WAIT_MS)
    }

    private fun hideSystemBars() {
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun goToMain() {
        if (navigated) return
        navigated = true
        splashImage.removeCallbacks(navigateRunnable)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        if (::splashImage.isInitialized) {
            splashImage.removeCallbacks(navigateRunnable)
        }
        super.onDestroy()
    }

    companion object {
        private const val INITIAL_SCALE = 0.94f
        private const val ENTRANCE_DURATION_MS = 900L
        private const val HOLD_AFTER_ENTRANCE_MS = 2_200L
        private const val MAX_SPLASH_WAIT_MS = 6_000L
    }
}
