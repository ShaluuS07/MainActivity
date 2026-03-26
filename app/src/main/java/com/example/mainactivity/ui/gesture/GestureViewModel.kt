package com.example.mainactivity.ui.gesture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.data.ProfileRepository
import com.example.mainactivity.ui.ProfileUi
import com.example.mainactivity.ui.toUi
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

class GestureViewModelFactory(
    private val repository: ProfileRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GestureViewModel::class.java)) {
            return GestureViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
