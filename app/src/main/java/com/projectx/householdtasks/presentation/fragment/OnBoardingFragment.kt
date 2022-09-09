package com.projectx.householdtasks.presentation.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.FragmentOnboardingBinding
import com.projectx.householdtasks.presentation.adapter.OnBoardingViewPagerAdapter
import com.projectx.householdtasks.presentation.viewmodel.OnBoardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingFragment : BaseFragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<OnBoardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOnboardingBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI().subscribeUI()
    }

    private fun FragmentOnboardingBinding.bindUI() = this.apply {
        viewPager.apply {
            adapter = OnBoardingViewPagerAdapter(childFragmentManager, lifecycle)
            getChildAt(0).apply {
                if (this is RecyclerView) setOverScrollMode(View.OVER_SCROLL_NEVER)
            }
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.apply {
                icon = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.flat_indicator,
                    requireContext().applicationContext.theme
                )
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    viewModel.onImageSelected(position = it)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    viewModel.onImageUnselected(position = it)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun FragmentOnboardingBinding.subscribeUI() = this.apply {

        val inLeft = AnimationUtils.loadAnimation(
            requireActivity(),
            android.R.anim.slide_in_left
        )
        val outRight = AnimationUtils.loadAnimation(
            requireActivity(),
            android.R.anim.slide_out_right
        )
        val inRight = AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.slide_in_right
        )
        val outLeft = AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.slide_out_left
        )

        viewModel.apply {

            positionChange.observe(viewLifecycleOwner) { positions ->
                val (previousPosition, currentPosition) = positions

                onboardingMainText.apply {
                    when {
                        previousPosition == null || currentPosition == null || previousPosition == currentPosition -> {
                            return@observe
                        }
                        previousPosition < currentPosition -> {
                            inAnimation = inRight
                            outAnimation = outLeft
                        }
                        else -> {
                            inAnimation = inLeft
                            outAnimation = outRight
                        }
                    }

                    setText(
                        resources.getString(
                            when (currentPosition) {
                                0 -> R.string.onboarding_main_text_1
                                1 -> R.string.onboarding_main_text_2
                                else -> R.string.onboarding_main_text_1
                            }
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}