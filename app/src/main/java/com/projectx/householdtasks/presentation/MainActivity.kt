package com.projectx.householdtasks.presentation

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.projectx.auth.data.authentication.Authentication
import com.projectx.common.presentation.BaseActivity
import com.projectx.householdtasks.R
import com.projectx.householdtasks.databinding.ActivityMainBinding
import com.projectx.householdtasks.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate) {

    override val viewModel by viewModel<MainViewModel>()

    override fun ActivityMainBinding.bindUI() {
        //TODO navigation between nav graphs
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = getNavController(R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        when (Authentication.isUserSignedIn()) {
            true -> {
                //TODO
                navGraph.setStartDestination(R.id.parent_nav_graph)
            }
            false -> {
                navGraph.setStartDestination(R.id.login_nav_graph)
            }
        }
        navController.graph = navGraph
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
                    viewModel.navigateToParentGraph()
                } else {
                    Log.w("MAIN", "signInWithCredential" + task.exception?.message)
                }
            }
    }

}