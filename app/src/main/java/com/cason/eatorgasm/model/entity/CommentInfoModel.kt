package com.cason.eatorgasm.model.entity

import java.io.Serializable

data class CommentInfoModel(
    var commentId: String = "",
    var boardId: String = "",
    var writerId: String = "",
    var writerName: String = "",
    var comment: String = "",
    var parentId: String = "",
    var parentName: String = "",
    var rootId: String = "",
    var mode: Boolean = false,
    var content: String = "",
    var createdTime: String = "",
    var modifiedTime: String = "",
    var photoUrl: String = "",
    var likeCount: Int = 0,
    var replyCount: Int = 0,
) : Serializable
