package com.projectx.householdtasks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.fragment.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        loadLoginFragment()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }

    private fun loadLoginFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, LoginFragment.newInstance("parent"))
            commit()
        }
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container_view, LoginFragment.newInstance("child"))
//            commit()
//        }
    }
}

