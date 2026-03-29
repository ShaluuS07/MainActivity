package com.example.mainactivity.mvvm.recommendations.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mainactivity.activity.MainActivity
import com.example.mainactivity.activity.MyApplication
import com.example.mainactivity.R
import com.example.mainactivity.core.viewModelsOf
import com.example.mainactivity.design.theme.MainActivityTheme
import com.example.mainactivity.mvvm.profile.view.ProfileDetailsFragment
import com.example.mainactivity.mvvm.recommendations.viewmodel.GestureViewModel
import kotlinx.coroutines.launch

class RecommendationsFragment : Fragment() {

    private var profileActionToast: Toast? = null

    private val viewModel: GestureViewModel by viewModelsOf {
        GestureViewModel((requireActivity().application as MyApplication).recommendationsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            clipChildren = false
            clipToPadding = false
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainActivityTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        GestureScreen(
                            viewModel = viewModel,
                            onBack = {
                                (activity as? MainActivity)?.navigateToHomeTab()
                            },
                            onOpenProfile = { profileId ->
                                findNavController().navigate(
                                    R.id.action_recommendationsFragment_to_profileDetailsFragment,
                                    bundleOf(ProfileDetailsFragment.ARG_PROFILE_ID to profileId),
                                )
                            },
                        )
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastEvents.collect { (name, accepted) ->
                    val message = if (accepted) {
                        getString(R.string.toast_removed_yes, name)
                    } else {
                        getString(R.string.toast_removed_no, name)
                    }
                    profileActionToast?.cancel()
                    profileActionToast = Toast.makeText(
                        requireContext().applicationContext,
                        message,
                        Toast.LENGTH_SHORT,
                    )
                    profileActionToast?.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profileActionToast?.cancel()
        profileActionToast = null
    }
}
