package com.cason.eatorgasm.viewmodel.screen

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.util.ToastManager
import com.cason.eatorgasm.view.PrivateView
import com.cason.eatorgasm.viewmodel.usecase.BoardUseCaseExecutor
import com.cason.eatorgasm.viewmodel.usecase.FetchMyProfileUseCaseExecutor
import com.cason.eatorgasm.viewmodel.usecase.LoginUsecaseExecutor
import com.google.android.gms.auth.api.signin.GoogleSignIn
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

    fun addBoardData(data: BoardInfoModel) {
        boardUsecase.addBoardData(data)
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

    fun getBoardListLiveData(): LiveData<ArrayList<BoardInfoModel>> {
        return boardUsecase.getBoardListLiveData()
    }

    fun getBoardLiveData(): LiveData<BoardInfoModel> {
        return boardUsecase.getBoardLiveData()
    }

}