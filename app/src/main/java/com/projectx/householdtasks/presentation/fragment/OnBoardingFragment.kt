package com.projectx.householdtasks.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
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

    private val inLeft by lazy { getAnimation(android.R.anim.slide_in_left) }
    private val outRight by lazy { getAnimation(android.R.anim.slide_out_right) }
    private val inRight by lazy { getAnimation(R.anim.slide_in_right) }
    private val outLeft by lazy { getAnimation(R.anim.slide_out_left) }

    private val unselectedIndicator by lazy { getDrawable(R.drawable.flat_indicator_unselected) }
    private val selectedIndicator by lazy { getDrawable(R.drawable.flat_indicator_selected) }

    private val selectedTab: View by lazy {
        layoutInflater.inflate(R.layout.flat_indicator_layout, null).apply {
            findViewById<View>(R.id.icon).background = selectedIndicator
        }
    }
    private val unselectedTab: View by lazy {
        layoutInflater.inflate(R.layout.flat_indicator_layout, null).apply {
            findViewById<View>(R.id.icon).background = unselectedIndicator
        }
    }

    private fun getAnimation(@AnimRes id: Int) =
        AnimationUtils.loadAnimation(requireActivity(), id)

    private fun getDrawable(@DrawableRes id: Int) =
        ResourcesCompat.getDrawable(resources, id, requireContext().applicationContext.theme)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOnboardingBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindUI().subscribeUI()

//        TODO: navigation
        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_chooseLoginTypeFragment)
        }
    }

    private fun FragmentOnboardingBinding.bindUI() = this.apply {
        setupViewPager()
        setupTabLayout()
    }

    private fun FragmentOnboardingBinding.subscribeUI() = this.apply {
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

    private fun FragmentOnboardingBinding.setupViewPager() {
        viewPager.apply {
            adapter = OnBoardingViewPagerAdapter(childFragmentManager, lifecycle)
            getChildAt(0).apply {
                if (this is RecyclerView) setOverScrollMode(View.OVER_SCROLL_NEVER)
            }
        }
    }

    private fun FragmentOnboardingBinding.setupTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = when (position) {
                0 -> selectedTab
                else -> unselectedTab
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    it.customView?.findViewById<View>(R.id.icon)?.background = selectedIndicator
                    viewModel.onImageSelected(position = it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let {
                    it.customView?.findViewById<View>(R.id.icon)?.background = unselectedIndicator
                    viewModel.onImageUnselected(position = it.position)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}