package com.projectx.householdtasks.presentation


import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projectx.householdtasks.R
import com.projectx.householdtasks.presentation.fragment.ParentHomescreenFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}