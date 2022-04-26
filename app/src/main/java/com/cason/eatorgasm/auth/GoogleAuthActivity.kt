package com.cason.eatorgasm.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cason.eatorgasm.R
import com.cason.eatorgasm.component.EatHomeActivity
import com.cason.eatorgasm.databinding.GoogleAuthActivityBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*


class GoogleAuthActivity : AppCompatActivity() {
    private lateinit var mBinding: GoogleAuthActivityBinding
    // Google Login result
    private val RC_SIGN_IN = 9001

    // Google Api Client
    private var googleSigninClient: GoogleSignInClient? = null

    // Firebase Auth
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = GoogleAuthActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // [START config_signin]
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSigninClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance();

        mBinding.googleLoginBtn.setOnClickListener {
            val signInIntent = googleSigninClient?.getSignInIntent()
            resultLauncher.launch(signInIntent)
        }
    }

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {

            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) {

                // 성공여부
                if (it.isSuccessful) {
                    googleSigninClient?.signOut()?.addOnCompleteListener {
                        showToast("로그아웃 되었습니다.")
                    }

                    val user = firebaseAuth?.currentUser
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@GoogleAuthActivity, EatHomeActivity::class.java))
                } else {

                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun showToast(txt: String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAG = "GoogleAuthActivity"
    }

}