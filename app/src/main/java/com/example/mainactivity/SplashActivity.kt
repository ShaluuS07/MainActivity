package com.example.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


class SplashActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private var navigated = false

    private val navigateTimeoutRunnable = Runnable { goToMain() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemBars()

        val playerView = findViewById<PlayerView>(R.id.playerView)
        playerView.useController = false

        val exo = ExoPlayer.Builder(this).build()
        player = exo
        playerView.player = exo
        exo.setMediaItem(MediaItem.fromUri(SPLASH_VIDEO_URI))
        exo.addListener(
            object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        goToMain()
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    goToMain()
                }
            },
        )
        exo.playWhenReady = true
        exo.prepare()

        playerView.postDelayed(navigateTimeoutRunnable, MAX_SPLASH_WAIT_MS)
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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        findViewById<PlayerView>(R.id.playerView)?.removeCallbacks(navigateTimeoutRunnable)
        player?.release()
        player = null
        super.onDestroy()
    }

    companion object {
        private const val SPLASH_VIDEO_URI = "https://videos.pexels.com/video-files/35592233/15083323_2730_1440_25fps.mp4"
        private const val MAX_SPLASH_WAIT_MS = 20_000L
    }
}
