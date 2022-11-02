package com.projectx.householdtasks.presentation.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import com.projectx.common.presentation.navigation.NavEvent
import com.projectx.common.presentation.viewmodel.BaseViewModel
import com.projectx.householdtasks.NavGraphDirections

class MainViewModel : BaseViewModel() {
    //TODO navigation between nav graphs

    override fun navigateTo(event: NavEvent.To, fragment: Fragment) {
        super.navigateTo(event, fragment)
        Log.d("MAIN", "navigateTo: $event")
        //findNavController(fragment.requireActivity(), R.id.nav_host_fragment).apply {
        //setGraph(R.navigation.nav_graph)
        //navigate(event.directions)
//            graph.setStartDestination(
//                when (event.directions) {
//                    NavGraphDirections.actionGlobalParentNavGraph() -> R.id.parent_nav_graph
//                    NavGraphDirections.actionGlobalChildNavGraph() -> R.id.child_nav_graph
//                    NavGraphDirections.actionGlobalLoginNavGraph() -> R.id.login_nav_graph
//                    else -> R.id.login_nav_graph
//                }
//            )
//            setGraph(
//                when (event.directions) {
//                    NavGraphDirections.actionGlobalParentNavGraph() -> com.projectx.householdtasks.parent.R.navigation.parent_nav_graph
//                    NavGraphDirections.actionGlobalChildNavGraph() -> com.projectx.householdtasks.child.R.navigation.child_nav_graph
//                    NavGraphDirections.actionGlobalLoginNavGraph() -> com.projectx.auth.R.navigation.login_nav_graph
//                    else -> R.navigation.nav_graph
//                }
//            )
        //}
    }

    fun navigateToParentGraph() {
        navigate(NavEvent.To(NavGraphDirections.actionGlobalParentNavGraph()))
    }

    fun navigateToChildGraph() {
        navigate(NavEvent.To(NavGraphDirections.actionGlobalChildNavGraph()))
    }

    fun navigateToLoginGraph() {
        navigate(NavEvent.To(NavGraphDirections.actionGlobalLoginNavGraph()))
    }
}