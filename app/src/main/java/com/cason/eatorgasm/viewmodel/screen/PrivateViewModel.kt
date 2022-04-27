package com.cason.eatorgasm.viewmodel.screen

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.util.ToastManager
import com.cason.eatorgasm.view.PrivateView
import com.cason.eatorgasm.viewmodel.usecase.LoginUsecaseExecutor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class PrivateViewModel @Inject constructor(
    private val handle: SavedStateHandle
    , private val repository: FirestoreRepository
    , private val usecase: LoginUsecaseExecutor): ViewModel(), PrivateView.ActionListener {

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

    fun requestSignOut() {
        usecase.signOut()
    }

    fun requestEditProfile(resultLauncher: ActivityResultLauncher<Intent>) {
//        val editProfileIntent = Intent(mActivityRef.get(), EditProfileActivity::class.java)
//        resultLauncher.launch(editProfileIntent)
    }

    fun googleResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            GoogleSignIn.getSignedInAccountFromIntent(result.data)
            usecase.firebaseAuthWithGoogle(result.data, mActivityRef.get() as AppCompatActivity)
        }
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

    override fun onRequestedSignIn(resultLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent: Intent = usecase.getSignInIntent()
        resultLauncher.launch(signInIntent)
    }

    override fun onRenderToast(msg: String?) {
        if (mActivityRef.get() != null) {
            ToastManager.INSTANCE.onMessage(mActivityRef.get()!!, msg)
        }
    }
}