package com.cason.eatorgasm.viewmodel.screen

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.SingleLiveEvent
import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.util.ToastManager
import com.cason.eatorgasm.view.PrivateView
import com.cason.eatorgasm.viewmodel.usecase.LoginUsecaseExecutor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class PrivateViewModel @Inject constructor(
    private val handle: SavedStateHandle
    , private val repository: FirestoreRepository
    , private val usecase: LoginUsecaseExecutor): ViewModel(), PrivateView.ActionListener {

    private val REQ_CODE_SIGN_IN = 1

    private lateinit var mActivityRef: WeakReference<Activity>

    fun setParentContext(parentContext: Activity) {
        mActivityRef = WeakReference(parentContext)
    }


    fun setPrivateView(view: PrivateView) {
        view.setActionListener(this)
        view.setFirebaseUserLiveData(usecase.getUserLiveData())
    }

    fun loadUserData() {
        usecase.loadUserData()
    }

    override fun onNotifySignInSuccess() {
        if (mActivityRef.get() == null) {
            return
        }
//        usecase.firebaseAuthWithGoogle(mActivityRef.get())
//        val intent = Intent(mActivityRef.get(), MainActivity::class.java)
//        mActivityRef.get()!!.startActivity(intent)
//        finishActivity()
    }

    override fun onRequestedSignIn(googleResultLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent: Intent = usecase.getSignInIntent()
        googleResultLauncher.launch(signInIntent)
    }

    fun googleResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            GoogleSignIn.getSignedInAccountFromIntent(result.data)
            usecase.firebaseAuthWithGoogle(result.data, mActivityRef.get() as AppCompatActivity)
        }
    }

    override fun onRenderToast(msg: String?) {
        if (mActivityRef.get() != null) {
//            ToastManager.INSTANCE.onMessage(mActivityRef.get()!!, msg)
//            mToastView.render(mActivityRef.get().getApplicationContext(), msg)
        }
    }



//    private val vmJob = Job()
//    protected val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

    private val _signInPrivate = SingleLiveEvent<Unit>()
    private val _singOutPrivate = SingleLiveEvent<Unit>()
    private val _requestEditProfile = SingleLiveEvent<Unit>()

    val requestSignIn: LiveData<Unit> get() = _signInPrivate
    val requestSignOut: LiveData<Unit> get() = _singOutPrivate
    val requestEditProfileActivity: LiveData<Unit> get() = _requestEditProfile

    fun requestSignIn() {
        _signInPrivate.call()
    }

    fun requestSignOut() {
        usecase.signOut()
//
//                googleSigninClient?.signOut()?.addOnCompleteListener {
//                    ToastManager.INSTANCE.onMessage(activity, "로그아웃 되었습니다.")
//                    updateUI()
//                }
    }

    fun requestEditProfile() {
        _requestEditProfile.call()
    }

}