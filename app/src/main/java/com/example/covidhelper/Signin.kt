package com.example.covidhelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.covidhelper.Daos.UserDao
import com.example.covidhelper.Models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class Signin : AppCompatActivity() {

    private val TAG = "SignInActivity Tag"
    private val RC_SIGN_IN: Int = 123
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener{
            signIn()
        }

        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun skipSignin(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSigninTask(task)
        }
    }

    private fun handleSigninTask(task: Task<GoogleSignInAccount>?) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task?.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credentials = GoogleAuthProvider.getCredential(idToken, null)
        signInButton.visibility = View.GONE
        logo.visibility = View.GONE
        progress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            val auth = auth.signInWithCredential(credentials).await()
            val firebaseUser = auth.user
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main){
                updateUI(firebaseUser)
            }
        }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null){
            val user = User(firebaseUser.uid,
                    firebaseUser.photoUrl.toString(),
                    firebaseUser.displayName)
            val userDao = UserDao()
            userDao.addUser(user)
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }
        else{
            signInButton.visibility = View.VISIBLE
            progress.visibility = View.GONE
        }

    }
}
