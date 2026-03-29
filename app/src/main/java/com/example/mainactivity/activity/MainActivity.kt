package com.example.mainactivity.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mainactivity.mvvm.you.viewmodel.YouTabUiViewModel
import com.example.mainactivity.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView
    private val youTabVm: YouTabUiViewModel by viewModels()
    private var youSubPageOpen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.apply {
            @Suppress("DEPRECATION")
            statusBarColor = android.graphics.Color.TRANSPARENT
            @Suppress("DEPRECATION")
            navigationBarColor = android.graphics.Color.TRANSPARENT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                isNavigationBarContrastEnforced = false
            }
        }
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        findViewById<View>(R.id.nav_host_fragment)?.let { host ->
            ViewCompat.setOnApplyWindowInsetsListener(host) { v, insets ->
                val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(bars.left, 0, bars.right, 0)
                insets
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(bottomNav) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, bars.bottom)
            insets
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.youFragment) {
                youSubPageOpen = false
            }
            updateBottomNavVisibility()
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when (navController.currentDestination?.id) {
                        R.id.profileDetailsFragment -> {
                            navController.navigateUp()
                        }
                        R.id.recommendationsFragment -> {
                            navigateToHomeTab()
                        }
                        R.id.youFragment -> {
                            if (youSubPageOpen) {
                                youSubPageOpen = false
                                updateBottomNavVisibility()
                                youTabVm.requestCloseSubPage()
                            } else {
                                navigateToHomeTab()
                            }
                        }
                        R.id.homeFragment -> {
                            finish()
                        }
                        else -> {
                            isEnabled = false
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            },
        )
    }

    fun navigateToHomeTab() {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            bottomNav.selectedItemId = R.id.homeFragment
            return
        }
        val popped = navController.popBackStack(R.id.homeFragment, false)
        if (popped) {
            bottomNav.selectedItemId = R.id.homeFragment
            return
        }
        navController.navigate(
            R.id.homeFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .setLaunchSingleTop(true)
                .build(),
        )
        bottomNav.selectedItemId = R.id.homeFragment
    }

    fun setYouSubPageOpen(open: Boolean) {
        youSubPageOpen = open
        updateBottomNavVisibility()
    }

    private fun updateBottomNavVisibility() {
        val dest = navController.currentDestination?.id
        bottomNav.visibility = when {
            dest == R.id.profileDetailsFragment -> View.GONE
            dest == R.id.youFragment && youSubPageOpen -> View.GONE
            else -> View.VISIBLE
        }
    }
}
