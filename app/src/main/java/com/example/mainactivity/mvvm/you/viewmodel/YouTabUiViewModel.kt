package com.example.mainactivity.mvvm.you.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/** Activity-scoped: MainActivity requests closing a You sub-page when hardware back is pressed. */
class YouTabUiViewModel : ViewModel() {

    private val _closeSubPage = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val closeSubPage: SharedFlow<Unit> = _closeSubPage.asSharedFlow()

    fun requestCloseSubPage() {
        _closeSubPage.tryEmit(Unit)
    }
}
