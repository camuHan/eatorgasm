package com.cason.eatorgasm.viewmodelimpl.usecase

import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.model.entity.UserInfoModel
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.modelimpl.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class FetchMyProfileUseCaseExecutorImpl @Inject constructor(private val mFirestoreRepository: FirestoreRepositoryImpl) : FetchMyProfileUseCaseExecutor {
    private val vmJob = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

    private val mUserInfoLiveData = MutableLiveData<UserInfoModel>()
    private val mFirebaseUserLiveData = MutableLiveData<FirebaseUser>()
    private val mThrowableLiveData = MutableLiveData<Throwable>()

    override fun fetchProfileData() {
        vmScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if(user!= null) {
                mUserInfoLiveData.postValue(mFirestoreRepository.fetchUserInfo(user))
            }
        }
    }

    override fun getUserInfoLiveData(): LiveData<UserInfoModel?> {
        return mUserInfoLiveData
    }
}