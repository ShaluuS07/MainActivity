package com.example.mainactivity.ui.profile

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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mainactivity.MyApplication
import com.example.mainactivity.ui.theme.MainActivityTheme

class ProfileDetailsFragment : Fragment() {

    private val profileId: Long by lazy {
        requireArguments().getLong(ARG_PROFILE_ID)
    }

    private val viewModel: ProfileDetailsViewModel by viewModels {
        ProfileDetailsViewModelFactory(
            (requireActivity().application as MyApplication).repository,
            profileId,
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
