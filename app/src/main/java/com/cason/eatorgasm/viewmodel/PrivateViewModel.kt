package com.cason.eatorgasm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PrivateViewModel: ViewModel() {
    private val vmJob = Job()
    protected val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

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
        _singOutPrivate.call()
    }

    fun requestEditProfile() {
        _requestEditProfile.call()
    }

}