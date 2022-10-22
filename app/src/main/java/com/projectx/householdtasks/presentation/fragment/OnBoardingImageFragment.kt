package com.projectx.householdtasks.presentation.fragment

import androidx.annotation.RawRes
import androidx.core.os.bundleOf
import com.projectx.householdtasks.databinding.FragmentOnboardingImageBinding
import com.projectx.householdtasks.presentation.viewmodel.OnBoardingImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingImageFragment :
    BaseFragment<FragmentOnboardingImageBinding, OnBoardingImageViewModel>(
        FragmentOnboardingImageBinding::inflate
    ) {

    override val viewModel by viewModel<OnBoardingImageViewModel>()

    companion object {
        fun getInstance(
            @RawRes animation: Int
        ) = OnBoardingImageFragment().apply {
            arguments = bundleOf(ANIMATION_ID to animation)
        }

        private const val ANIMATION_ID = "ANIMATION_ID"
    }

    override fun FragmentOnboardingImageBinding.bindUI() {
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
}