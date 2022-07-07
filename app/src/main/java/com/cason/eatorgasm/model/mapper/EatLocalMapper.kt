package com.cason.eatorgasm.model.mapper

import com.cason.eatorgasm.model.entity.BoardInfoModel
import com.cason.eatorgasm.model.entity.CommentInfoModel
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
            val tempList = documentSnapshot.data?.get("contentsList")
            if(tempList is ArrayList<*>) {
                tempList.forEach {
                    item.contentsList?.add(it.toString())
                }
            }
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

    fun mapToCommentInfo(documentSnapshot: DocumentSnapshot?): CommentInfoModel? {
        var commentInfo: CommentInfoModel? = null
        if(documentSnapshot != null) {
            val item = CommentInfoModel()
            item.commentId = documentSnapshot.id
            item.boardId = documentSnapshot.data?.get("boardId").toString()
            item.writerId = documentSnapshot.data?.get("writerId").toString()
            item.writerName = documentSnapshot.data?.get("writerName").toString()
            item.comment = documentSnapshot.data?.get("comment").toString()
            item.parentId = documentSnapshot.data?.get("parentId").toString()
            item.parentName = documentSnapshot.data?.get("parentName").toString()
            item.rootId = documentSnapshot.data?.get("rootId").toString()
            item.mode = documentSnapshot.data?.get("mode") as Boolean
            item.content = documentSnapshot.data?.get("content").toString()

            item.createdTime = documentSnapshot.data?.get("createdTime").toString()
            item.modifiedTime = documentSnapshot.data?.get("modifiedTime").toString()
            item.photoUrl = documentSnapshot.data?.get("photoUrl").toString()
            item.likeCount = Integer.parseInt(documentSnapshot.data?.get("likeCount").toString())
            item.replyCount = Integer.parseInt(documentSnapshot.data?.get("replyCount").toString())
//            item.locked = it.getData()?.get("locked").toString() as Boolean
            commentInfo = item
        }
        return commentInfo
    }

    fun mapToCommentInfoList(documentList: List<DocumentSnapshot>?): ArrayList<CommentInfoModel> {
        val commentInfoList = ArrayList<CommentInfoModel>()
        documentList?.forEach { documentSnapshot ->
            val item = mapToCommentInfo(documentSnapshot)
            if(item != null) {
                commentInfoList.add(item)
            }
        }
        return commentInfoList
    }
}