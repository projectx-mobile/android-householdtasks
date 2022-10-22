package com.projectx.householdtasks.presentation.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.projectx.householdtasks.presentation.viewmodel.BaseViewModel

class NavigationObserver(
    val viewModel: BaseViewModel,
    val navigateBack: (Fragment) -> Unit = ::navigateBack,
    val navigateTo: (NavEvent.To, Fragment) -> Unit = ::navigateTo,
    val navigateBackToRoot: (Fragment) -> Unit = ::navigateBackToRoot
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
}

// Navigation is always executed in the same way
private fun navigateTo(event: NavEvent.To, fragment: Fragment) {
    findNavController(fragment).navigate(event.directions)
}

private fun navigateUp(fragment: Fragment) {
    findNavController(fragment).navigateUp()
}

private fun navigateBack(fragment: Fragment) {
    findNavController(fragment).popBackStack()
}

private fun navigateBackToRoot(fragment: Fragment) {
    fragment.requireActivity().finishAfterTransition()
}