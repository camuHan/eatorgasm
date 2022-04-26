package com.cason.eatorgasm.viewmodelimpl.usecase

import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.model.entity.EatUserProfileItem
import com.cason.eatorgasm.model.FirestoreRepository
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.modelimpl.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FetchMyProfileUseCaseExecutorImpl @Inject constructor(private val mFirestoreRepository: FirestoreRepositoryImpl) : FetchMyProfileUseCaseExecutor {
    private val mUserInfoLiveData = MutableLiveData<EatUserProfileItem>()
    private val mFirebaseUserLiveData = MutableLiveData<FirebaseUser>()
    private val mThrowableLiveData = MutableLiveData<Throwable>()

    override fun fetchProfileData() {
        val auth = FirebaseAuth.getInstance()
        mUserInfoLiveData.value = mFirestoreRepository.fetchUserInfo(auth.currentUser!!)
    }

    override fun getUserInfoLiveData(): LiveData<EatUserProfileItem?>? {
        return mUserInfoLiveData
    }
}