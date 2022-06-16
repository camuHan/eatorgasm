package com.cason.eatorgasm.viewmodel.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.R
import com.cason.eatorgasm.model.FirestoreRepositoryImpl
import com.cason.eatorgasm.util.ToastManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginUsecaseExecutorImpl @Inject constructor(private val mFirestoreRepository: FirestoreRepositoryImpl, @ApplicationContext private val context: Context) : LoginUsecaseExecutor {
    private val vmJob = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

    private val mFirebaseUserLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData<FirebaseUser?>()
    private val mThrowableLiveData: MutableLiveData<Throwable> = MutableLiveData<Throwable>()
    private val mGoogleSignInClient: GoogleSignInClient
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun loadUserData() {
        vmScope.launch {
            mAuth.currentUser?.reload()?.await()
            val user = mAuth.currentUser
            if (user != null) {
                mFirebaseUserLiveData.postValue(user)
            }
        }
    }

    override fun getUserLiveData(): LiveData<FirebaseUser?> {
        return mFirebaseUserLiveData
    }

    override fun getThrowableLiveData(): LiveData<Throwable> {
        return mThrowableLiveData
    }

    override fun getSignInIntent(): Intent {
        return mGoogleSignInClient.signInIntent
    }


    override fun firebaseAuthWithGoogle(data: Intent?, activity: Activity) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account: GoogleSignInAccount = try {
            task.getResult<ApiException>(ApiException::class.java)
        } catch (e: ApiException) {
            e.printStackTrace()
            mThrowableLiveData.setValue(e)
            return
        }
        val credential: AuthCredential =
            GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) {
                // 성공여부
                if (it.isSuccessful) {
                    ToastManager.INSTANCE.onMessage(activity, "로그인 성공")
                    uploadUserInfoToRemoteDb()
                } else {
                    ToastManager.INSTANCE.onMessage(activity, "로그인 실패")
                }
            }
    }

    private fun uploadUserInfoToRemoteDb() {
        vmScope.launch {
            mFirestoreRepository.addUserIfNotExists(mAuth.currentUser!!)
            loadUserData()
        }
    }

    override fun signOut() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener {
            mFirebaseUserLiveData.value = null
        }
    }

    init {
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
    }
}