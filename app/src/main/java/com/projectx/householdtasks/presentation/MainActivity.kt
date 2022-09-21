package com.projectx.householdtasks.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.fragment.*

class MainActivity : AppCompatActivity(), ProfileFragment.OnEditProfileListener, EditProfileEmailFragment.OnBackPressedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        loadLoginFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, ProfileFragment())
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

    override fun onEditName() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, EditProfileFragment())
            addToBackStack("EditProfile")
            commit()
        }
    }

    override fun onEditEmail() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, EditProfileEmailFragment())
            addToBackStack("EditEmail")
            commit()
        }
    }

    override fun onEditPassword() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, EditProfilePasswordFragment())
            addToBackStack("EditPassword")
            commit()
        }
    }

    override fun onBackButtonPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, ProfileFragment())
            commit()
        }
    }
}

