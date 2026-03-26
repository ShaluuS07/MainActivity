package com.example.mainactivity.ui.home

import androidx.lifecycle.MutableLiveData
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

class HomeViewModel(
    private val repository: ProfileRepository,
) : ViewModel() {

    private val _profiles = MutableStateFlow<List<ProfileUi>>(emptyList())
    val profiles: StateFlow<List<ProfileUi>> = _profiles.asStateFlow()

    val pendingSubtitle = MutableLiveData<String>()
    val newBadgeText = MutableLiveData<String>()
    val newBadgeVisible = MutableLiveData<Boolean>()
    val showPager = MutableLiveData<Boolean>()
    val showEmpty = MutableLiveData<Boolean>()

    init {
        pendingSubtitle.value = ""
        newBadgeText.value = ""
        newBadgeVisible.value = false
        showPager.value = false
        showEmpty.value = true
    }

    private val _toastEvents = MutableSharedFlow<Pair<String, Boolean>>(extraBufferCapacity = 1)
    val toastEvents: SharedFlow<Pair<String, Boolean>> = _toastEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.observeProfiles().collectLatest { list ->
                val ui = list.map { it.toUi() }
                _profiles.value = ui
                pendingSubtitle.value = "${ui.size} Profiles pending with me"
                newBadgeText.value = "${ui.size} NEW"
                newBadgeVisible.value = ui.isNotEmpty()
                showPager.value = ui.isNotEmpty()
                showEmpty.value = ui.isEmpty()
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

class HomeViewModelFactory(
    private val repository: ProfileRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
