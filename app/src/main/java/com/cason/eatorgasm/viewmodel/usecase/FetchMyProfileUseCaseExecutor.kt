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
    fun getUpdateProfileImageResultLiveData(): LiveData<Uri>
    fun updateProfileData(data: UserInfoModel)
    fun updateProfileImage(uri: Uri)
}