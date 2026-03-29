package com.example.mainactivity.mvvm.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mainactivity.activity.MyApplication
import com.example.mainactivity.core.viewModelsOf
import com.example.mainactivity.design.theme.MainActivityTheme
import com.example.mainactivity.mvvm.profile.viewmodel.ProfileDetailsViewModel

class ProfileDetailsFragment : Fragment() {

    private val args: ProfileDetailsFragmentArgs by navArgs()

    private val viewModel: ProfileDetailsViewModel by viewModelsOf {
        ProfileDetailsViewModel(
            (requireActivity().application as MyApplication).profileDetailsRepository,
            args.profileId,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val vm = viewModel
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainActivityTheme {
                    val profile by vm.profile.collectAsStateWithLifecycle()
                    Surface(modifier = Modifier.fillMaxSize()) {
                        ProfileDetailsScreen(
                            profile = profile,
                            onBack = { this@ProfileDetailsFragment.findNavController().popBackStack() },
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val ARG_PROFILE_ID = "profileId"
    }
}
