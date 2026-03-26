package com.example.mainactivity.mvvm.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.mvvm.home.model.HomeUiState
import com.example.mainactivity.mvvm.home.HomeRepository
import com.example.mainactivity.mvvm.profile.model.ProfileUi
import com.example.mainactivity.mvvm.profile.model.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState.initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /** For ViewPager / Compose collectors — derived from [uiState]. */
    val profiles: Flow<List<ProfileUi>> = uiState.map { it.profiles }

    /** Data Binding: LiveData at the boundary only (XML cannot bind to StateFlow). */
    val pendingSubtitle: LiveData<String> = uiState.map { it.pendingSubtitle }.asLiveData()
    val newBadgeText: LiveData<String> = uiState.map { it.newBadgeText }.asLiveData()
    val newBadgeVisible: LiveData<Boolean> = uiState.map { it.newBadgeVisible }.asLiveData()
    val showPager: LiveData<Boolean> = uiState.map { it.showPager }.asLiveData()
    val showEmpty: LiveData<Boolean> = uiState.map { it.showEmpty }.asLiveData()

    private val _toastEvents = MutableSharedFlow<Pair<String, Boolean>>(extraBufferCapacity = 1)
    val toastEvents: SharedFlow<Pair<String, Boolean>> = _toastEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.observeProfiles().collectLatest { list ->
                val ui = list.map { it.toUi() }
                _uiState.value = HomeUiState(
                    profiles = ui,
                    pendingSubtitle = "${ui.size} Profiles pending with me",
                    newBadgeText = "${ui.size} NEW",
                    newBadgeVisible = ui.isNotEmpty(),
                    showPager = ui.isNotEmpty(),
                    showEmpty = ui.isEmpty(),
                )
            }
        }
    }

    fun removeProfile(profile: ProfileUi, accepted: Boolean) {
        viewModelScope.launch {
            val name = profile.name
            repository.dismiss(profile.id)
            _toastEvents.emit(name to accepted)
        }
    }
}
