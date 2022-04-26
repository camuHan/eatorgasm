package com.cason.eatorgasm.viewmodel.usecase

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

/**
 * @author firefly2.kim
 * @since 19. 8. 24
 */
interface LoginUsecaseExecutor {
    fun loadUserData()
    fun getUserLiveData(): LiveData<FirebaseUser?>
    fun getThrowableLiveData(): LiveData<Throwable>
    fun firebaseAuthWithGoogle(data: Intent?, activity: Activity)
    fun getSignInIntent(): Intent
    fun signOut()
}