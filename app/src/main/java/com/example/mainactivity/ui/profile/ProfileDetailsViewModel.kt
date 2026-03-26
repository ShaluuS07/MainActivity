package com.example.mainactivity.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.data.ProfileRepository
import com.example.mainactivity.ui.ProfileUi
import com.example.mainactivity.ui.toUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProfileDetailsViewModel(
    repository: ProfileRepository,
    profileId: Long,
) : ViewModel() {

    val profile: StateFlow<ProfileUi?> = repository.observeProfile(profileId)
        .map { entity -> entity?.toUi() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
}
