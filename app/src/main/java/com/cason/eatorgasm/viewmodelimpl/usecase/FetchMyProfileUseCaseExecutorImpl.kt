package com.cason.eatorgasm.viewmodelimpl.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.modelimpl.FirestoreRepositoryImpl
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
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

    private val mUpdateUserInfo = MutableLiveData<Boolean>()
    private val mUpdateProfileImage = MutableLiveData<String>()
    private val mChangeProfileImage = MutableLiveData<String>()

    override fun getUserInfoLiveData(): LiveData<UserInfoModel?> {
        return mUserInfoLiveData
    }

    override fun fetchProfileData() {
        vmScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if(user!= null) {
                mUserInfoLiveData.postValue(mFirestoreRepository.fetchUserInfo(user))
            }
        }
    }

    override fun getUpdateProfileResultLiveData(): LiveData<Boolean> {
        return mUpdateUserInfo
    }

    override fun getUpdateProfileImageResultLiveData(): LiveData<String> {
        return mUpdateProfileImage
    }

    override fun updateProfileData(data: UserInfoModel) {
        vmScope.launch {
            // TODO remove this
            mFirestoreRepository.updateUserToFirestore(data)
            val url = mFirestoreRepository.uploadProfileImageInStorage(Uri.parse(data.photoUrl))
            if(url != null) {
                val result = mFirestoreRepository.updateProfile(data)
                mUpdateUserInfo.postValue(result)
                mUpdateProfileImage.postValue(data.photoUrl)
            }
        }
    }

    override fun getChangeProfileImageResultLiveData(): LiveData<String> {
        return mChangeProfileImage
    }

    override fun changeProfileImage(uri: Uri) {
        mChangeProfileImage.postValue(uri.toString())
    }

    override fun fetchProfileImage() {
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null && user.photoUrl != null) {
            mUpdateProfileImage.postValue(user.photoUrl.toString())
        }
    }
}