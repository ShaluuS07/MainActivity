package com.example.mainactivity.mvvm.you.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class YouTabUiViewModel : ViewModel() {

    private val _closeSubPage = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val closeSubPage: SharedFlow<Unit> = _closeSubPage.asSharedFlow()

    fun requestCloseSubPage() {
        _closeSubPage.tryEmit(Unit)
    }
}
