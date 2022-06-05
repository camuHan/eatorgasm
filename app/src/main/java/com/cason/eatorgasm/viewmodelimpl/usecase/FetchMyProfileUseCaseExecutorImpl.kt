package com.cason.eatorgasm.viewmodelimpl.usecase

import android.net.Uri
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.model.entity.UserInfoModel
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.modelimpl.FirestoreRepositoryImpl
import com.cason.eatorgasm.util.ProgressManager
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

    override fun getUserInfoLiveData(): LiveData<UserInfoModel?> {
        return mUserInfoLiveData
    }

    override fun fetchProfileData() {
        vmScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if(user!= null) {
                mUserInfoLiveData.postValue(mFirestoreRepository.fetchUserInfo(user))
                fetchProfileImage()
            }
        }
    }

    override fun getUpdateProfileResultLiveData(): LiveData<Boolean> {
        return mUpdateUserInfo
    }

    override fun updateProfileData(data: UserInfoModel) {
        vmScope.launch {
            val result = mFirestoreRepository.updateUserToFirestore(data)
            mUpdateUserInfo.postValue(result)
        }
    }

    override fun getUpdateProfileImageResultLiveData(): LiveData<String> {
        return mUpdateProfileImage
    }

    override fun updateProfileImage(uri: Uri) {
        vmScope.launch {
            val result = mFirestoreRepository.uploadProfileImageInStorage(uri)
            if(result != null) {
                val map = HashMap<String, Any>()
                map["image"] = result
                if(mFirestoreRepository.uploadProfileImage(map)) {
                    val snapshot = mFirestoreRepository.fetchProfileImage() ?: return@launch
                    mUpdateProfileImage.postValue(snapshot.data!!["image"] as String)
                }
            }
        }
    }

    override fun fetchProfileImage() {
        vmScope.launch {
            val snapshot = mFirestoreRepository.fetchProfileImage()
            if(snapshot != null && snapshot.data != null) {
                val test = snapshot.data!!["image"] as String
                mUpdateProfileImage.postValue(test)
            }
        }
    }
}