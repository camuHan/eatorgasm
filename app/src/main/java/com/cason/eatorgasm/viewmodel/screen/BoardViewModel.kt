package com.cason.eatorgasm.viewmodel.screen

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.util.ProgressManager
import com.cason.eatorgasm.view.PrivateView
import com.cason.eatorgasm.viewmodel.usecase.BoardUseCaseExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val handle: SavedStateHandle
    , private val boardUsecase: BoardUseCaseExecutor
): ViewModel() {

    private lateinit var mActivityRef: WeakReference<Activity>

    fun setParentContext(parentContext: Activity) {
        mActivityRef = WeakReference(parentContext)
    }

    fun setPrivateView(view: PrivateView) {
//        view.setActionListener(this)
//        view.setFirebaseUserLiveData(boardUsecase.getUserLiveData())
//        view.setUpdateProfileImageLiveData(profileUsecase.getUpdateProfileImageResultLiveData())
    }

    fun setBoardData(data: BoardInfoModel) {
        ProgressManager.showProgressCircular(CMEnum.CommonProgressType.UPLOAD, "", null)
        boardUsecase.setBoardData(data)
    }

    fun deleteBoardDataByBoardId(boardId: String?) {
        boardUsecase.deleteBoardData(boardId)
    }

    fun getBoardDataByBoardId(boardId: String) {
        boardUsecase.fetchBoardDataByBoardId(boardId)
    }

    fun updateBoardDataList() {
        boardUsecase.fetchBoardDataList()
    }

    fun getUpdateBoardLiveData(): LiveData<Boolean> {
        return boardUsecase.getUpdateBoardLiveData()
    }

    fun getBoardListLiveData(): LiveData<ArrayList<BoardInfoModel>> {
        return boardUsecase.getBoardListLiveData()
    }

    fun getBoardLiveData(): LiveData<BoardInfoModel> {
        return boardUsecase.getBoardLiveData()
    }

}