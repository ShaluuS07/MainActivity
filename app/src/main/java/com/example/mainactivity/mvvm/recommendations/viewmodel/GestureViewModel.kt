package com.example.mainactivity.mvvm.recommendations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.mvvm.profile.ProfileRepository
import com.example.mainactivity.data.local.ProfileUi
import com.example.mainactivity.data.local.toUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GestureViewModel(
    private val repository: ProfileRepository,
) : ViewModel() {

    private val _profiles = MutableStateFlow<List<ProfileUi>>(emptyList())
    val profiles: StateFlow<List<ProfileUi>> = _profiles.asStateFlow()

    private val _toastEvents = MutableSharedFlow<Pair<String, Boolean>>(extraBufferCapacity = 1)
    val toastEvents: SharedFlow<Pair<String, Boolean>> = _toastEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.observeProfiles().collectLatest { list ->
                _profiles.value = list.map { it.toUi() }
            }
        }
    }

    fun removeProfile(profile: ProfileUi, accepted: Boolean) {
        viewModelScope.launch {
            val name = profile.name
            repository.deleteById(profile.id)
            _toastEvents.emit(name to accepted)
        }
    }
}
