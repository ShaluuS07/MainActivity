package com.example.mainactivity.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel


inline fun <reified VM : ViewModel> Fragment.viewModelsOf(
    crossinline factory: () -> VM,
) = viewModels<VM> { viewModelFactory(factory) }
