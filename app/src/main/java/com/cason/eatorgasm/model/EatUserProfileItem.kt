package com.cason.eatorgasm.model

import com.cason.eatorgasm.R

data class EatUserProfileItem(
    var userId: Long = 0,
    var strName: String? = "",
    var email: String? = "",
    var password: String ?= "",
    var phoneNumber: String? = "",
    var privateImageId: Int = R.drawable.ic_home_tab_private
)
