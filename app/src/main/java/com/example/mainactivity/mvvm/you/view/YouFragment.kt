package com.example.mainactivity.mvvm.you.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mainactivity.design.theme.MainActivityTheme
import com.example.mainactivity.mvvm.you.viewmodel.YouTabUiViewModel

class YouFragment : Fragment() {

    private val youTabVm: YouTabUiViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainActivityTheme {
                    YouScreen(youTabVm = youTabVm)
                }
            }
        }
    }
}
