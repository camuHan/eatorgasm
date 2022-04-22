package com.cason.eatorgasm.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cason.eatorgasm.R
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.model.EatUserProfileItem
import com.cason.eatorgasm.util.ToastManager
import com.cason.eatorgasm.view.profile.EditProfileActivity
import com.cason.eatorgasm.viewmodel.PrivateViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PrivateFragment : Fragment() {

    companion object {
        fun newInstance() = PrivateFragment()
    }

    private lateinit var mBinding: PrivateFragmentBinding
    private val mPrivateViewModel: PrivateViewModel by viewModels()

    // Google Api Client
    private var googleSigninClient: GoogleSignInClient? = null
    // Firebase Auth
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = PrivateFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.vm = mPrivateViewModel
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSigninClient = GoogleSignIn.getClient(requireActivity(), gso)

        firebaseAuth = FirebaseAuth.getInstance();

        initViewModelCallback()
        updateUI()

        mBinding.btnPrivateGoogleSignIn.setOnClickListener {
            mPrivateViewModel.requestSignIn()
        }

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initViewModelCallback() {
        with(mPrivateViewModel) {
            requestSignIn.observe(this@PrivateFragment, Observer {
                val signInIntent = googleSigninClient?.getSignInIntent()
                googleResultLauncher.launch(signInIntent)
            })

            requestSignOut.observe(this@PrivateFragment, Observer {
                Firebase.auth.signOut()

                googleSigninClient?.signOut()?.addOnCompleteListener {
                    ToastManager.INSTANCE.onMessage(activity, "로그아웃 되었습니다.")
                    updateUI()
                }
            })

            requestEditProfileActivity.observe(this@PrivateFragment, Observer {
                val editProfileIntent = Intent(requireContext(), EditProfileActivity::class.java)
                editProfileResultLauncher.launch(editProfileIntent)
            })
        }
    }

    private val googleResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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

    private val editProfileResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) {

                // 성공여부
                if (it.isSuccessful) {
                    val user = firebaseAuth?.currentUser
                    ToastManager.INSTANCE.onMessage(activity, "로그인 성공")
                    updateUI()
//                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this@GoogleAuthActivity, EatHomeActivity::class.java))
                } else {
                    ToastManager.INSTANCE.onMessage(activity, "로그인 실패")
                }
            }
    }

    private fun updateUI() {
        val user = Firebase.auth.currentUser
        val profile = EatUserProfileItem()

        if(user == null) {
            profile.strName = ""
            profile.email = ""
            profile.phoneNumber = ""
        } else {
            profile.strName = user.displayName
            profile.email = user.email
            profile.phoneNumber = user.phoneNumber
        }

        mBinding.profile = profile
    }

}