package com.projectx.householdtasks.presentation.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.fragment.OnBoardingImageFragment

class OnBoardingViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int) = OnBoardingImageFragment.getInstance(
        when (position) {
            0 -> R.raw.onboarding_animation_1
            else -> R.raw.onboarding_animation_2
        }
    )

    private companion object {
        const val NUM_TABS = 2
    }
}