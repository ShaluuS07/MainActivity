package com.example.mainactivity.mvvm.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.mvvm.profile.ProfileDetailsRepository
import com.example.mainactivity.mvvm.profile.model.ProfileUi
import com.example.mainactivity.mvvm.profile.model.toUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProfileDetailsViewModel(
    repository: ProfileDetailsRepository,
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
