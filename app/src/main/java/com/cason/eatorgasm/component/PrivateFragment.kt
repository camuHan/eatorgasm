package com.cason.eatorgasm.component

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cason.eatorgasm.databinding.PrivateFragmentBinding
import com.cason.eatorgasm.util.ToastManager
import com.cason.eatorgasm.view.PrivateView
import com.cason.eatorgasm.viewimpl.PrivateViewImpl
import com.cason.eatorgasm.viewmodel.screen.HomeViewModel
import com.cason.eatorgasm.viewmodel.screen.PrivateViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrivateFragment : Fragment() {
    lateinit var mPrivateView: PrivateViewImpl

    private lateinit var mBinding: PrivateFragmentBinding
    private val mHomeViewModel: HomeViewModel by activityViewModels()
    private val mPrivateViewModel: PrivateViewModel by viewModels()

    // Google Api Client
    private var googleSigninClient: GoogleSignInClient? = null
    // Firebase Auth
    private var firebaseAuth: FirebaseAuth? = null

    private val googleResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        mPrivateViewModel.googleResult(result)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = PrivateFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPrivateViewModel.setParentContext(requireActivity())
        mPrivateViewModel.setPrivateView(PrivateViewImpl(mBinding, this))
        mBinding.vm = mPrivateViewModel

        mPrivateViewModel.loadUserData()




//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
        // [END config_signin]

//        googleSigninClient = GoogleSignIn.getClient(requireActivity(), gso)

        firebaseAuth = FirebaseAuth.getInstance();

        initViewModelCallback()
        updateUI()

        mBinding.btnPrivateGoogleSignIn.setOnClickListener {
            mPrivateViewModel.onRequestedSignIn(googleResultLauncher)
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initViewModelCallback() {
//        with(mPrivateViewModel) {
//            requestSignIn.observe(this@PrivateFragment, Observer {
//                val signInIntent = googleSigninClient?.getSignInIntent()
//                googleResultLauncher.launch(signInIntent)
//            })
//
//            requestSignOut.observe(this@PrivateFragment, Observer {
//                Firebase.auth.signOut()
//
//                googleSigninClient?.signOut()?.addOnCompleteListener {
//                    ToastManager.INSTANCE.onMessage(activity, "로그아웃 되었습니다.")
//                    updateUI()
//                }
//            })
//
//            requestEditProfileActivity.observe(this@PrivateFragment, Observer {
//                val editProfileIntent = Intent(context, EditProfileActivity::class.java)
//                editProfileResultLauncher.launch(editProfileIntent)
//            })
//        }
    }

    private val editProfileResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

        }
    }

    private fun updateUI() {
    }

}