package com.cason.eatorgasm.model.entity

import java.io.Serializable

data class BoardInfoModel(
    var userId: String = "",
    var boardId: String = "",
    var publisher: String = "",
    var name: String = "",
    var title: String = "",
    var contents: String = "",
    var contentsList: ArrayList<String>? = ArrayList(),
    var location: String = "",
    var password: String = "",
    var createdTime: String = "",
    var modifiedTime: String = "",
    var timeStamp: String = "",
    var photoUrl: String = "",
    var likeCount: Int = 0,
    var commentCount: Int = 0,
    var viewCount: Int = 0,
    var locked: Boolean = false
) : Serializable
