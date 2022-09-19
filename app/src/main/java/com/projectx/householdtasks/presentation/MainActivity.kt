package com.projectx.householdtasks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.fragment.EditProfileEmailFragment
import com.projectx.householdtasks.presentation.fragment.EditProfileFragment
import com.projectx.householdtasks.presentation.fragment.LoginFragment
import com.projectx.householdtasks.presentation.fragment.ProfileFragment

class MainActivity : AppCompatActivity(), ProfileFragment.OnEditProfileListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fragment_container_view, ProfileFragment())
//            commit()
//        }

        loadLoginFragment()
    }

    override fun onEditName() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, EditProfileFragment())
            commit()
        }
    }

    override fun onEditEmail() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, EditProfileEmailFragment())
            commit()
        }
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