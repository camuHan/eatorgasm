package com.cason.eatorgasm.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.SingleLiveEvent

class EatAuthViewModel : ViewModel() {
    var isResendPhoneAuth: Boolean = false

    val etPhoneNum = MutableLiveData<String>("")
    val etAuthNum = MutableLiveData<String>("")

    private val _requestPhoneAuth = MutableLiveData<Boolean>()
    private val _requestResendPhoneAuth = MutableLiveData<Boolean>()
    private val _authComplete = SingleLiveEvent<Unit>()

    val requestPhoneAuth: LiveData<Boolean> get() = _requestPhoneAuth
    val requestResendPhoneAuth: LiveData<Boolean> get() = _requestResendPhoneAuth
    val authComplete: LiveData<Unit> get() = _authComplete

    fun requestPhoneAuth() {
        if (!isResendPhoneAuth) { //첫 폰인증
            _requestPhoneAuth.value = !etPhoneNum.value.isNullOrBlank()
            //isResendPhoneAuth = true
        } else { //재시도
            _requestResendPhoneAuth.value = !etPhoneNum.value.isNullOrBlank()
        }
    }

    fun authComplete() {
        _authComplete.call()
    }

    companion object {
        private val TAG = "EatAuthViewModel"
    }
}