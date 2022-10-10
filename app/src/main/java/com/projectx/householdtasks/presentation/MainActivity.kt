package com.projectx.householdtasks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.ActivityMainBinding
import com.projectx.householdtasks.presentation.fragment.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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