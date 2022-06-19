package com.cason.eatorgasm.model.mapper

import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.google.firebase.firestore.DocumentSnapshot


object EatLocalMapper {
    fun mapToBoardInfo(documentSnapshot: DocumentSnapshot?): BoardInfoModel? {
        var boardInfo: BoardInfoModel? = null
        if(documentSnapshot != null) {
            val item = BoardInfoModel()
            item.boardId = documentSnapshot.id
            item.userId = documentSnapshot.data?.get("userId").toString()
            item.publisher = documentSnapshot.data?.get("publisher").toString()
            item.name = documentSnapshot.data?.get("name").toString()
            item.title = documentSnapshot.data?.get("title").toString()
            item.contents = documentSnapshot.data?.get("contents").toString()
            item.password = documentSnapshot.data?.get("password").toString()
            item.timeStamp = documentSnapshot.data?.get("timeStamp").toString()
            item.photoUrl = documentSnapshot.data?.get("photoUrl").toString()
            item.location = documentSnapshot.data?.get("location").toString()
//            item.locked = it.getData()?.get("locked").toString() as Boolean
            boardInfo = item
        }
        return boardInfo
    }

    fun mapToBoardInfoList(documentList: List<DocumentSnapshot>?): ArrayList<BoardInfoModel> {
        val boardInfoList = ArrayList<BoardInfoModel>()
        documentList?.forEach { documentSnapshot ->
            val item = mapToBoardInfo(documentSnapshot)
            if(item != null) {
                boardInfoList.add(item)
            }
        }
        return boardInfoList
    }
}