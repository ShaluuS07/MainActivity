package com.example.mainactivity.mvvm.home.model

import com.example.mainactivity.mvvm.profile.model.ProfileUi

/**
 * Single source of truth for the Home tab UI. [profiles] and binding fields stay in sync.
 */
data class HomeUiState(
    val profiles: List<ProfileUi> = emptyList(),
    val pendingSubtitle: String = "",
    val newBadgeText: String = "",
    val newBadgeVisible: Boolean = false,
    val showPager: Boolean = false,
    val showEmpty: Boolean = true,
) {
    companion object {
        val initial = HomeUiState(showEmpty = true)
    }
}
