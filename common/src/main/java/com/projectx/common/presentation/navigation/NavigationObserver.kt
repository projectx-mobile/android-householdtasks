package com.projectx.common.presentation.navigation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.projectx.common.presentation.viewmodel.BaseViewModel

class NavigationObserver(
    val viewModel: BaseViewModel,
    val navigateBack: (Fragment) -> Unit = viewModel::navigateBack,
    val navigateTo: (NavEvent.To, Fragment) -> Unit = viewModel::navigateTo,
    val navigateBackToRoot: (Fragment) -> Unit = viewModel::navigateBackToRoot
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewCreated(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fragmentManager, fragment, view, savedInstanceState)
        fragment.lifecycleScope.launchWhenStarted {
            viewModel.navEventFlow.collect { event ->
                when (event) {
                    is NavEvent.To -> navigateTo(event, fragment)
                    NavEvent.Up -> navigateUp(fragment)
                    NavEvent.Back -> navigateBack(fragment)
                    NavEvent.BackToRoot -> navigateBackToRoot(fragment)
                }
            }
        }
    }

    // Called from Fragments
    fun register(fragment: Fragment) {
        fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(this, false)
    }
    // Called from Activities
    fun register(activity: AppCompatActivity) {
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, false)
    }

    companion object {
        // Navigation is always executed in the same way
        fun navigateTo(event: NavEvent.To, fragment: Fragment) {
            findNavController(fragment).navigate(event.directions)
        }

        fun navigateUp(fragment: Fragment) {
            findNavController(fragment).navigateUp()
        }

        fun navigateBack(fragment: Fragment) {
            findNavController(fragment).popBackStack()
        }

        fun navigateBackToRoot(fragment: Fragment) {
            fragment.requireActivity().finishAfterTransition()
        }
    }
}