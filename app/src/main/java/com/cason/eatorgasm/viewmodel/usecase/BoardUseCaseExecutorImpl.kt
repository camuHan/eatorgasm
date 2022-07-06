package com.cason.eatorgasm.viewmodel.usecase

import androidx.lifecycle.MutableLiveData
import com.cason.eatorgasm.define.EatDefine.FireBaseStorage.FIREBASE_STORAGE_BOARD_IMAGES
import com.cason.eatorgasm.define.EatDefine.FireBaseStorage.FIREBASE_STORAGE_COMMENT_IMAGE
import com.cason.eatorgasm.define.EatDefine.FireStoreCollection.COLLECTION_NAME_BOARDS
import com.cason.eatorgasm.define.EatDefine.FireStoreCollection.COLLECTION_NAME_COMMENTS
import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.model.mapper.EatLocalMapper
import com.cason.eatorgasm.model.FirestoreRepositoryImpl
import com.cason.eatorgasm.model.entity.CommentInfoModel
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

    private val mUpdateBoardInfo = MutableLiveData<Boolean>()
    private val mUpdateCommentInfo = MutableLiveData<Boolean>()

    private val mBoardInfo = MutableLiveData<BoardInfoModel>()
    private val mBoardInfoList = MutableLiveData<ArrayList<BoardInfoModel>>()

    override fun setBoardData(boardData: BoardInfoModel) {
        vmScope.launch {
            val user = Firebase.auth.currentUser
            if(user != null) {
                boardData.userId = user.uid
                if(user.displayName != null) {
                    boardData.name = user.displayName!!
                    boardData.publisher = user.displayName!!
                }
                boardData.timeStamp = System.currentTimeMillis().toString()  // for test
                boardData.location = ""
                boardData.photoUrl = user.photoUrl.toString()
            }

            val result = if(boardData.boardId != "") {
                boardData.modifiedTime = System.currentTimeMillis().toString()
                val boardStorageName = FIREBASE_STORAGE_BOARD_IMAGES + "/" + boardData.boardId
                boardData.contentsList = mFirestoreRepository.uploadImageListInStorage(boardStorageName, boardData.contentsList)
                mFirestoreRepository.setFireStoreDataByDocumentId(COLLECTION_NAME_BOARDS, boardData, boardData.boardId!!)
            } else {
                boardData.createdTime = System.currentTimeMillis().toString()
                boardData.boardId = boardData.userId + boardData.timeStamp
                val boardStorageName = FIREBASE_STORAGE_BOARD_IMAGES + "/" + boardData.boardId
                boardData.contentsList = mFirestoreRepository.uploadImageListInStorage(boardStorageName, boardData.contentsList)
                mFirestoreRepository.setFireStoreData(COLLECTION_NAME_BOARDS, boardData)
            }
            mUpdateBoardInfo.postValue(result)
        }
    }

    override fun updateBoardData(boardData: BoardInfoModel) {
        TODO("Not yet implemented")
    }

    override fun deleteBoardData(boardId: String?) {
        vmScope.launch {
            mFirestoreRepository.deleteBoardByBoardId(boardId)
        }
    }

    override fun fetchBoardDataByBoardId(boardId: String) {
        vmScope.launch {
            val result = mFirestoreRepository.fetchBoard(boardId)
            if(result != null) {
                val item = EatLocalMapper.mapToBoardInfo(result) ?: return@launch
                mBoardInfo.postValue(item)
            }
        }
    }

    override fun fetchBoardDataList() {
        vmScope.launch {
            val result = mFirestoreRepository.fetchBoardList()
            if(result != null) {
                val list = EatLocalMapper.mapToBoardInfoList(result)
                mBoardInfoList.postValue(list)
            }
        }
    }

    override fun setCommentData(commentData: CommentInfoModel) {
        vmScope.launch {
            val user = Firebase.auth.currentUser
            if (user != null) {
                commentData.writerId = user.uid
                if(user.displayName != null) {
                    commentData.writerName = user.displayName!!
                }
                commentData.photoUrl = user.photoUrl.toString()
            }

            if (commentData.commentId != null && commentData.commentId != "") {
                commentData.modifiedTime = System.currentTimeMillis().toString()
            } else {
                commentData.createdTime = System.currentTimeMillis().toString()
                commentData.commentId = commentData.writerId + commentData.createdTime
            }
            val boardStorageName = FIREBASE_STORAGE_COMMENT_IMAGE + "/" + commentData.commentId
            commentData.content = mFirestoreRepository.uploadImageInStorage(boardStorageName, commentData.content) ?: ""
            val result = mFirestoreRepository.setFireStoreSubDataByDocumentId(
                COLLECTION_NAME_BOARDS,
                commentData.boardId,
                COLLECTION_NAME_COMMENTS,
                commentData.commentId,
                commentData)

            mUpdateCommentInfo.postValue(result)
        }
    }

    override fun getUpdateBoardLiveData(): MutableLiveData<Boolean> {
        return mUpdateBoardInfo
    }

    override fun getBoardLiveData(): MutableLiveData<BoardInfoModel> {
        return mBoardInfo
    }

    override fun getBoardListLiveData(): MutableLiveData<ArrayList<BoardInfoModel>> {
        return mBoardInfoList
    }
}