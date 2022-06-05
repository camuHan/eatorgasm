package com.cason.eatorgasm.model.entity

import com.cason.eatorgasm.R
import com.google.firebase.auth.FirebaseUser

data class BoardInfoModel(
    var userId: String? = "",
    var name: String? = "",
    var title: String? = "",
    var contents: String? = "",
    var password: String ?= "",
)
