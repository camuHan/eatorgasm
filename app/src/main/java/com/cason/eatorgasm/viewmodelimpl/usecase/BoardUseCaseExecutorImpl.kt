package com.cason.eatorgasm.viewmodelimpl.usecase

import android.net.Uri
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.model.entity.UserInfoModel
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.modelimpl.FirestoreRepositoryImpl
import com.cason.eatorgasm.viewmodel.usecase.BoardUseCaseExecutor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class BoardUseCaseExecutorImpl @Inject constructor(private val mFirestoreRepository: FirestoreRepositoryImpl) :
    BoardUseCaseExecutor {
    private val vmJob = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

//    private val mUserInfoLiveData = MutableLiveData<UserInfoModel>()
//    private val mFirebaseUserLiveData = MutableLiveData<FirebaseUser>()
//    private val mThrowableLiveData = MutableLiveData<Throwable>()
//
//    private val mUpdateUserInfo = MutableLiveData<Boolean>()
//    private val mUpdateProfileImage = MutableLiveData<Uri>()

    override fun addBoardData(data: BoardInfoModel) {
        TODO("Not yet implemented")
    }

    override fun updateBoardData(data: BoardInfoModel) {
        TODO("Not yet implemented")
    }

    override fun fetchBoardDataList() {
        TODO("Not yet implemented")
    }
}