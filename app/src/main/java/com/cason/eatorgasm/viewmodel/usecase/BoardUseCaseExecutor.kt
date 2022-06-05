package com.cason.eatorgasm.viewmodel.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.model.entity.UserInfoModel
import java.net.URI

interface BoardUseCaseExecutor {

    fun addBoardData(data: BoardInfoModel)
    fun updateBoardData(data: BoardInfoModel)
    fun fetchBoardDataList()
}