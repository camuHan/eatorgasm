package com.cason.eatorgasm.viewmodel.usecase

import androidx.lifecycle.LiveData
import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.model.entity.EatUserProfileItem
import com.google.firebase.auth.FirebaseUser

interface FetchMyProfileUseCaseExecutor {
//    val userInfoLiveData: LiveData<EatUserProfileItem?>?
//    fun getUserLiveData(): LiveData<FirebaseUser>

    fun getUserInfoLiveData(): LiveData<EatUserProfileItem?>?
    fun fetchProfileData()
}