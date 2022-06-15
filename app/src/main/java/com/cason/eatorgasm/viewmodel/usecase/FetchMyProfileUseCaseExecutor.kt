package com.cason.eatorgasm.viewmodel.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.model.entity.UserInfoModel
import java.net.URI

interface FetchMyProfileUseCaseExecutor {
//    val userInfoLiveData: LiveData<EatUserProfileItem?>?
//    fun getUserLiveData(): LiveData<FirebaseUser>

    fun getUserInfoLiveData(): LiveData<UserInfoModel?>
    fun fetchProfileData()

    fun getUpdateProfileResultLiveData(): LiveData<Boolean>
    fun getUpdateProfileImageResultLiveData(): LiveData<String>
    fun updateProfileData(data: UserInfoModel)

    fun getChangeProfileImageResultLiveData(): LiveData<String>
    fun changeProfileImage(uri: Uri)
    fun fetchProfileImage()
}