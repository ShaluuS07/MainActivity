package com.example.mainactivity.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
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
        setupBottomNavWithNavController()

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
            syncBottomNavSelectedItem(destination)
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

    /**
     * Same idea as [NavigationUI.setupWithNavController], but **Home** always uses
     * [navigateToHomeTab] so we never rely on [NavigationUI.onNavDestinationSelected] alone
     * (it can fail or leave a bad state with multiple back stacks).
     *
     * Also handles **reselect**: if Home is already checked but the current screen is not Home
     * (common after Home → Recommendations → You → Back), the default listener is never called
     * for Home — only [NavigationBarView.OnItemReselectedListener] runs, which [setupWithNavController]
     * does not set — so we add that here.
     */
    private fun setupBottomNavWithNavController() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navigateToHomeTab()
                    true
                }
                else -> NavigationUI.onNavDestinationSelected(item, navController)
            }
        }
        bottomNav.setOnItemReselectedListener { item ->
            if (item.itemId == R.id.homeFragment &&
                navController.currentDestination?.id != R.id.homeFragment
            ) {
                navigateToHomeTab()
            }
        }
    }

    private fun syncBottomNavSelectedItem(destination: NavDestination) {
        if (destination is FloatingWindow) return
        bottomNav.menu.forEach { item ->
            if (destination.hierarchy.any { it.id == item.itemId }) {
                item.isChecked = true
            }
        }
    }

    /**
     * Returns to Home and syncs the bottom nav.
     *
     * Do not use [NavController.popBackStack] to [R.id.homeFragment] alone: with bottom navigation’s
     * multiple back stacks (save/restore state), Back from **You** after **Home → Recommendations**
     * can pop only **You** and surface **Recommendations** while the bottom bar still shows Home
     * selected — then tapping Home is a no-op (reselect). Clearing the graph and navigating to Home
     * fixes both the fragment and the selection.
     */
    fun navigateToHomeTab() {
        if (navController.currentDestination?.id == R.id.homeFragment) {
            bottomNav.selectedItemId = R.id.homeFragment
            return
        }
        navController.navigate(
            R.id.homeFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, /* inclusive = */ true)
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
