package com.projectx.householdtasks.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setBottomNavViewVisibility(false)
        setBottomNavClickListener()
        setCheckedItemBottomNavigation()
    }

    fun setBottomNavViewVisibility(isVisible: Boolean) {
        when {
            isVisible -> binding.navigation.root.visibility = View.VISIBLE
            !isVisible -> binding.navigation.root.visibility = View.INVISIBLE
        }
    }
    private fun setBottomNavClickListener() {
        binding.navigation.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.parentHomescreenFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.parentHomescreenFragment)
                    true
                }
                R.id.wallet -> {
                    // TODO: navigation
                    true
                }
                R.id.awards -> {
                    // TODO: navigation
                    true
                }
                R.id.profile -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCheckedItemBottomNavigation() {
        binding.navigation.bottomNavigation.menu.getItem(4).isChecked = true
    }

}