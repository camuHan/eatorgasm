package com.cason.eatorgasm.viewmodelimpl.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.SingleLiveEvent
import com.cason.eatorgasm.viewmodel.screen.PrivateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PrivateViewModelImpl: ViewModel() {
    private val vmJob = Job()
    protected val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

    private val _signInPrivate = SingleLiveEvent<Unit>()
    private val _singOutPrivate = SingleLiveEvent<Unit>()
    private val _requestEditProfile = SingleLiveEvent<Unit>()

    val requestSignIn: LiveData<Unit> get() = _signInPrivate
    val requestSignOut: LiveData<Unit> get() = _singOutPrivate
    val requestEditProfileActivity: LiveData<Unit> get() = _requestEditProfile

//    override fun requestSignIn() {
//        _signInPrivate.call()
//    }
//
//    override fun requestSignOut() {
//        _singOutPrivate.call()
//    }
//
//    override fun requestEditProfile() {
//        _requestEditProfile.call()
//    }

}