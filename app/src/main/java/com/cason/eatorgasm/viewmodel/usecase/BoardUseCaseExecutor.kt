package com.cason.eatorgasm.viewmodel.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.model.entity.UserInfoModel
import java.net.URI

interface BoardUseCaseExecutor {

    fun setBoardData(data: BoardInfoModel)
    fun updateBoardData(data: BoardInfoModel)

    fun deleteBoardData(boardId: String?)

    fun fetchBoardDataByBoardId(boardId: String)
    fun fetchBoardDataList()

    fun getUpdateBoardLiveData(): MutableLiveData<Boolean>
    fun getBoardLiveData(): MutableLiveData<BoardInfoModel>
    fun getBoardListLiveData(): MutableLiveData<ArrayList<BoardInfoModel>>
}