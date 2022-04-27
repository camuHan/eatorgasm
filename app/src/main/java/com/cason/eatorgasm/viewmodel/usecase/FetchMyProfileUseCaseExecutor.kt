package com.cason.eatorgasm.viewmodel.usecase

import androidx.lifecycle.LiveData
import com.cason.eatorgasm.model.entity.UserInfoModel

interface FetchMyProfileUseCaseExecutor {
//    val userInfoLiveData: LiveData<EatUserProfileItem?>?
//    fun getUserLiveData(): LiveData<FirebaseUser>

    fun getUserInfoLiveData(): LiveData<UserInfoModel?>
    fun fetchProfileData()
}