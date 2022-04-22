package com.cason.eatorgasm.model

import com.cason.eatorgasm.R

data class EatUserProfileItem(
    var userId: Long = 0,
    var email: String? = "",
    var password: String ?= "",
    var phoneNumber: String? = "",
    var strName: String? = "",
    var privateImageId: Int = R.drawable.home_tab_private_icon
)
