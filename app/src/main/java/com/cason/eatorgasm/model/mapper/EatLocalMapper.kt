package com.cason.eatorgasm.model.mapper

import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.google.firebase.firestore.DocumentSnapshot


object EatLocalMapper {
    fun mapToBoardInfoList(documentList: List<DocumentSnapshot>?): ArrayList<BoardInfoModel> {
        val boardInfoList = ArrayList<BoardInfoModel>()
        documentList?.forEach {
            val item = BoardInfoModel()
            item.userId = it.data?.get("userId").toString()
            item.publisher = it.data?.get("publisher").toString()
            item.name = it.data?.get("name").toString()
            item.title = it.data?.get("title").toString()
            item.contents = it.data?.get("contents").toString()
            item.password = it.data?.get("password").toString()
            item.timeStamp = it.data?.get("timeStamp").toString()
            item.photoUrl = it.data?.get("photoUrl").toString()
            item.location = it.data?.get("location").toString()
//            item.locked = it.getData()?.get("locked").toString() as Boolean
            boardInfoList.add(item)
        }
        return boardInfoList
    }
}