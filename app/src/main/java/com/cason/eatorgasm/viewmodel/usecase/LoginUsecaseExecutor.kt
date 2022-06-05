package com.cason.eatorgasm.viewmodel.usecase

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

interface LoginUsecaseExecutor {
    fun loadUserData()
    fun getUserLiveData(): LiveData<FirebaseUser?>
    fun getThrowableLiveData(): LiveData<Throwable>
    fun firebaseAuthWithGoogle(data: Intent?, activity: Activity)
    fun getSignInIntent(): Intent
    fun signOut()
}