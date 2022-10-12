package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.core.os.bundleOf
import com.projectx.householdtasks.databinding.FragmentOnboardingImageBinding
import com.projectx.householdtasks.presentation.viewmodel.OnBoardingImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingImageFragment : BaseFragment() {

    private var _binding: FragmentOnboardingImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<OnBoardingImageViewModel>()

    companion object {
        fun getInstance(
            @RawRes animation: Int
        ) = OnBoardingImageFragment().apply {
            arguments = bundleOf(ANIMATION_ID to animation)
        }

        private const val ANIMATION_ID = "ANIMATION_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOnboardingImageBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI()
    }

    private fun FragmentOnboardingImageBinding.bindUI() {
        arguments?.let { arg ->
            when (val animationId = arg.getInt(ANIMATION_ID)) {
                0 -> {

                }
                else -> {
                    animation.setAnimation(animationId)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}