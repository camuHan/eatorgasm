package com.cason.eatorgasm.viewmodel.usecase

import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.model.mapper.EatLocalMapper
import com.cason.eatorgasm.model.FirestoreRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class BoardUseCaseExecutorImpl @Inject constructor(private val mFirestoreRepository: FirestoreRepositoryImpl) :
    BoardUseCaseExecutor {
    private val vmJob = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + vmJob)

    private val mBoardInfoLiveData = MutableLiveData<BoardInfoModel>()
    private val mUpdateBoardInfo = MutableLiveData<Boolean>()

    private val mBoardInfoList = MutableLiveData<ArrayList<BoardInfoModel>>()

    override fun addBoardData(data: BoardInfoModel) {
        vmScope.launch {
            val user = Firebase.auth.currentUser
            if(user != null) {
                data.userId = user.uid
                data.name = user.displayName
                data.publisher = user.displayName
                data.timeStamp = System.currentTimeMillis().toString()
                data.location = ""
                data.photoUrl = user.photoUrl.toString()
            }
            val result = mFirestoreRepository.uploadBoard(data)
            mUpdateBoardInfo.postValue(result)
        }
    }

    override fun updateBoardData(data: BoardInfoModel) {
        TODO("Not yet implemented")
    }

    override fun fetchBoardDataList() {
        vmScope.launch {
            val result = mFirestoreRepository.fetchBoardList()
            if(result != null) {
                val list = EatLocalMapper.mapToBoardInfoList(result)
//                var list = ArrayList<BoardInfoModel>()
//                list.addAll(result)
                mBoardInfoList.postValue(list)
            }
        }
    }

    override fun getBoardListLiveData(): MutableLiveData<ArrayList<BoardInfoModel>> {
        return mBoardInfoList
    }
}