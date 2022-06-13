package com.cason.eatorgasm.model.entity

data class BoardInfoModel(
    var userId: String? = "",
    var publisher: String? = "",
    var name: String? = "",
    var title: String? = "",
    var contents: String? = "",
    var location: String? = "",
    var password: String? = "",
    var timeStamp: String? = "",
    var photoUrl: String? = "",
    var locked: Boolean = false
)
