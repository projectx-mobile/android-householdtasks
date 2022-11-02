package com.projectx.householdtasks.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.projectx.householdtasks.R
import com.projectx.householdtasks.data.example.authentication.Authentication
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Authentication.REQUEST_CODE_GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            result?.let {
                handleSignInResult(result)
            }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount

            // you can store user data to SharedPreference
            val credential: AuthCredential =
                GoogleAuthProvider.getCredential(account?.idToken, null)
            firebaseAuthWithGoogle(credential)
        } else {
            // Google Sign In failed, update UI appropriately
            Log.e("MAIN", "Login Unsuccessful. $result")
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(credential: AuthCredential) {
        Authentication.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                Log.d("MAIN", "signInWithCredential:onComplete:" + task.isSuccessful)
                if (task.isSuccessful) {
                    //TODO save/update credentials & other
//                    viewModel.navigateToParentGraph()
                } else {
                    Log.w("MAIN", "signInWithCredential" + task.exception?.message)
                }
            }
    }

}