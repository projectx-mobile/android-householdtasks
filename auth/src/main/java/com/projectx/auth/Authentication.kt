package com.projectx.auth

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Authentication {

    //TODO
    private val auth by lazy { Firebase.auth }

    fun getToken() {
        //TODO
    }

    fun signInWithGoogle(activity: AppCompatActivity) {
        //TODO
    }

    fun signIn(email: String, password: String) {
        //TODO
    }

    fun signOut() {
        Firebase.auth.signOut()
    }

    private const val TAG = "AUTH"
    private const val WEB_CLIENT_ID =
        "420886722619-r9q7hqrf7sfvdlt28a7n24q4ma7s6i1m.apps.googleusercontent.com"
}