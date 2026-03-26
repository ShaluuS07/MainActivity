package com.example.mainactivity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.mainactivity.MyApplication
import com.example.mainactivity.R
import com.example.mainactivity.core.viewModelsOf
import com.example.mainactivity.databinding.FragmentHomeBinding
import com.example.mainactivity.ui.profile.ProfileDetailsFragment
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModelsOf {
        HomeViewModel((requireActivity().application as MyApplication).repository)
    }

    private lateinit var pagerAdapter: ProfilePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = ProfilePagerAdapter(
            onYes = { profile -> viewModel.removeProfile(profile, accepted = true) },
            onNo = { profile -> viewModel.removeProfile(profile, accepted = false) },
            onCardClick = { profile ->
                findNavController().navigate(
                    R.id.action_homeFragment_to_profileDetailsFragment,
                    bundleOf(ProfileDetailsFragment.ARG_PROFILE_ID to profile.id),
                )
            },
        )
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = 2
        val marginPx = resources.getDimensionPixelOffset(R.dimen.viewpager_page_margin)
        binding.viewPager.setPageTransformer(MarginPageTransformer(marginPx))

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profiles.collect { list ->
                    val previousIndex = binding.viewPager.currentItem
                    pagerAdapter.submitList(list)
                    if (list.isEmpty()) return@collect
                    val safeIndex = when {
                        previousIndex >= list.size -> list.size - 1
                        else -> previousIndex
                    }
                    if (binding.viewPager.currentItem != safeIndex) {
                        binding.viewPager.setCurrentItem(safeIndex, false)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastEvents.collect { (name, accepted) ->
                    val message = if (accepted) {
                        getString(R.string.toast_removed_yes, name)
                    } else {
                        getString(R.string.toast_removed_no, name)
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
        _binding = null
    }
}
