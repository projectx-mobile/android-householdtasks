package com.projectx.householdtasks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.fragment.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        loadLoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, ProfileFragment())
            commit()
        }

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container_view, InviteUserByEmailFragment())
//            commit()
//        }
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container_view, AccountStatusFragment())
//            commit()
//        }
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container_view, NotificationFragment())
//            commit()
//        }
    }

    private fun loadLoginFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, LoginFragment.newInstance("parent"))
            commit()
        }
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container_view, LoginFragment.newInstance("child"))
//            commit()
//        }
    }
}

