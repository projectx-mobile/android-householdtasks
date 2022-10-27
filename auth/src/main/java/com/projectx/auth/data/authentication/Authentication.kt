package com.projectx.auth.data.authentication

import android.app.PendingIntent
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.os.bundleOf
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.projectx.auth.R

object Authentication {

    val auth by lazy { Firebase.auth }

    fun isUserSignedIn() = auth.currentUser != null

    fun isUserSignedInWithGoogle(): Boolean =
        if (isUserSignedIn()) auth.currentUser?.providerId == "google.com" else false

    fun getToken() {
        //TODO
    }

    private fun signIn() {
        //TODO
    }

    fun getGoogleSignInTask(data: Intent?) = GoogleSignIn.getSignedInAccountFromIntent(data)

    fun signInWithGoogle(activity: AppCompatActivity) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(activity, signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN, bundleOf())
        //TODO
        //Google
        //AuthCredentials in firebase
    }

    fun signIn(email: String, password: String) {
        //TODO
    }

    fun signOut() {
        auth.signOut()
    }

    private const val TAG = "AUTH"
    const val REQUEST_CODE_GOOGLE_SIGN_IN = 1 /* unique request id */
}